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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.activities.MainActivity;
import com.taxiproject.group6.taxiapp.activities.MapsActivity;
import com.taxiproject.group6.taxiapp.activities.PersonalDetailsActivity;
import com.taxiproject.group6.taxiapp.classes.LoadToDatabase;
import com.taxiproject.group6.taxiapp.classes.User;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private Button buttonLogIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLogin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    public static User newUser;

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
                if (task.isSuccessful() && isServiceOK()){
                    //Login
                    newUser = LoadToDatabase.loadFromDatabase();
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
    public static User getUser() {
        return newUser;
    }

}
