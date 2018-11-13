package com.taxiproject.group6.taxiapp.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.activities.MainActivity;
import com.taxiproject.group6.taxiapp.activities.MapsActivity;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.User;

import java.util.Calendar;
import java.util.Objects;

import static com.firebase.ui.auth.AuthUI.TAG;

public class RegisterFragment extends Fragment {

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private Button buttonRegister;
    private EditText editTextEmail, editTextPassword, editTextConfirmPassword
            , editTextFirstName, editTextLastName;
    private TextView textViewSignIn, textViewDateOfBirth;
    private boolean passwordsMatch;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        buttonRegister = view.findViewById(R.id.buttonRegister);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        textViewDateOfBirth = view.findViewById(R.id.textViewDateOfBirth);

        textViewSignIn = view.findViewById(R.id.textViewSignIn);

        passwordsMatch = false;

        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                if(password.length() > 0 && s.length() > 0) {
                    if (password.equals(confirmPassword)) {
                        editTextConfirmPassword.getBackground().setColorFilter(Color.parseColor("#00ff00"), PorterDuff.Mode.DARKEN);
                        editTextPassword.getBackground().setColorFilter(Color.parseColor("#00ff00"), PorterDuff.Mode.DARKEN);
                        passwordsMatch = true;
                    } else {
                        editTextConfirmPassword.getBackground().setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.DARKEN);
                        editTextPassword.getBackground().setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.DARKEN);
                        passwordsMatch = false;
                    }
                }
            }
        });

        buttonRegister.setOnClickListener(v -> registerUser());
        textViewSignIn.setOnClickListener(v -> goToLogInPage());
        textViewDateOfBirth.setOnClickListener(v -> showDatePicker());
//        getDateFromDialog();
        dateSetListener = (v, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            textViewDateOfBirth.setText(date);
        };
        return view;
    }

    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                Objects.requireNonNull(getContext()),
                R.style.DatePickerStyle,
                dateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0Xaa000000));

        dialog.show();
    }

    public void getDateFromDialog(){
        dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            textViewDateOfBirth.setText(date);
        };
    }

    private void goToLogInPage() {
        ((MainActivity)Objects.requireNonNull(getActivity())).setViewPager(0);
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //no email
            Toast.makeText(getActivity(),"Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //no password
            Toast.makeText(getActivity(),"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        ProgressDialog progressDialog = ((MainActivity)(Objects.requireNonNull(getActivity()))).getProgressDialog();
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        if(passwordsMatch) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.cancel();
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Register Successfully", Toast.LENGTH_SHORT).show();
                                User newUser = new User();
                                newUser.setEmailAddress(editTextEmail.getText().toString());
                                newUser.setUid(firebaseAuth.getUid());

                                DatabaseConnector.loadToDatabase(newUser);

                                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful() && isServiceOK()) {
                                            //Login
                                            DatabaseConnector.loadToDatabase(newUser);
                                            Intent i = new Intent(getActivity(), MapsActivity.class);
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getActivity(), "Email is already registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Could not register. Please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }else{
            Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isServiceOK(){
        Log.d(TAG, "isServiceOK:  checking google  services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.getActivity());

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServiceOK: Google Play is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServiceOK: error occurred but  we can fix  it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this.getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this.getActivity(), "You  can't make  map requests", Toast.LENGTH_SHORT).show();
        }
        return  false;
    }
}
