package com.taxiproject.group6.taxiapp.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.taxiproject.group6.taxiapp.classes.LoadToDatabase;
import com.taxiproject.group6.taxiapp.classes.User;

import java.util.Objects;

import static com.firebase.ui.auth.AuthUI.TAG;
import static com.taxiproject.group6.taxiapp.classes.LoadToDatabase.loadToDatabase;

public class RegisterFragment extends Fragment {

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private Button buttonRegister;
    private EditText editTextEmail, editTextPassword, editTextNickName, editTextName, editTextDOB, editTextPNo;
    private TextView textViewSignIn;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        buttonRegister = view.findViewById(R.id.buttonRegister);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextNickName = view.findViewById(R.id.editTextNickName);
        editTextName = view.findViewById(R.id.editTextName);
        editTextDOB = view.findViewById(R.id.editTextDOB);
        editTextPNo = view.findViewById(R.id.editTextPNo);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        textViewSignIn = view.findViewById(R.id.textViewSignIn);

        buttonRegister.setOnClickListener(v -> registerUser());
        textViewSignIn.setOnClickListener(v -> goToLogInPage());

        return view;
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

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.cancel();
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"Register Successfully", Toast.LENGTH_SHORT).show();
                            User newUser = new User();
                            newUser.setNickName(editTextNickName.getText().toString());
                            newUser.setFullName(editTextName.getText().toString());
                            newUser.setDateOfBirth(editTextDOB.getText().toString());
                            newUser.setPhoneNumber(editTextPNo.getText().toString());



                            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful() && isServiceOK()){
                                        //Login
                                        LoadToDatabase.loadToDatabase(newUser);
                                        Intent i = new Intent(getActivity(), MapsActivity.class);
                                        startActivity(i);
                                    }
                                    else{
                                        Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getActivity(), "Email is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity(),"Could not register. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
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
