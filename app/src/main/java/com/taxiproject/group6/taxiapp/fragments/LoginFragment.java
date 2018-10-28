package com.taxiproject.group6.taxiapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.activities.MainActivity;
import com.taxiproject.group6.taxiapp.activities.MapsActivity;
import com.taxiproject.group6.taxiapp.activities.PersonalDetailsActivity;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private Button buttonLogIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLogin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        buttonLogIn = view.findViewById(R.id.buttonLogIn);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        textViewLogin = view.findViewById(R.id.textViewRegisterAcc);

        buttonLogIn.setOnClickListener(v -> userLogin());
        textViewLogin.setOnClickListener(v -> goToRegisterPage());

        return view;
    }

    private void userLogin(){
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
        progressDialog.setMessage("Login...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Login
                    Intent i = new Intent(getActivity(), MapsActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToRegisterPage(){
        ((MainActivity)Objects.requireNonNull(getActivity())).setViewPager(1);
    }
}
