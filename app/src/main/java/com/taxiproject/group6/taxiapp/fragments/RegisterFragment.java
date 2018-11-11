package com.taxiproject.group6.taxiapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.activities.MainActivity;
import com.taxiproject.group6.taxiapp.classes.User;

import java.util.Objects;

public class RegisterFragment extends Fragment {

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
                            User.setNickname(editTextNickName.getText().toString());
                            User.setFull_name(editTextName.getText().toString());
                            User.setDate_of_birth(editTextDOB.getText().toString());
                            User.setPhone_number(editTextPNo.getText().toString());
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
}
