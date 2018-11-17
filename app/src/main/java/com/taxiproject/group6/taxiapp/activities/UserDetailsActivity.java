package com.taxiproject.group6.taxiapp.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.User;

public class UserDetailsActivity extends AppCompatActivity {

    private EditText userNameEditText, firstNameEditText, lastNameEditText, phoneNumberEditText;
    private User user;
    private boolean changed;
    private Button changeEmailAddressButton;
    private static final String TAG = "UserDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        changed = false;
        user = DatabaseConnector.loadFromDatabase();

        changeEmailAddressButton = findViewById(R.id.changeEmailAddressButton);
        userNameEditText = findViewById(R.id.userNameEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);

        //Log.d("USERNAME:onCreate():", user.getUserName());

        init();

        changeEmailAddressButton.setOnClickListener(v -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserDetailsActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_confirmation,null);
            EditText new_email = (EditText) mView.findViewById(R.id.new_email);
            EditText confirm_password = (EditText) mView.findViewById(R.id.confirm_password);
            mBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(),confirm_password.getText().toString());

                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "User re-authenticated.");
                                    if(task.isSuccessful()){
                                        updateUserEmail(user, new_email.getText().toString());
                                    } else {
                                        // Password is incorrect
                                    }
                                }
                            });
                }
                });
            mBuilder.setNegativeButton("Cancel", null);

            mBuilder.setView(mView);
            AlertDialog dialog =mBuilder.create();
            dialog.show();
        });
    }

    private void updateUserEmail(FirebaseUser user, String email) {
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });
    }

    private void init(){
        userNameEditText.setText(user.getUserName());
        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        phoneNumberEditText.setText(user.getPhoneNumber());
    }

    public void confirmButtonPressed(View view) {
        if(changed)
            DatabaseConnector.updateAllDetails(user);
        closeActivity();
    }

    public void cancelButtonPressed(View view) {
        closeActivity();
    }


    public void closeActivity(){
        this.finish();
    }

    public void editButtonPressed(View view) {
        String tag = view.getTag().toString();
        Log.d("TAG----------", tag);
        EditText et;
        switch (tag){
            case "userName": et = userNameEditText;
                break;
            case "firstName": et = firstNameEditText;
                break;
            case "lastName": et = lastNameEditText;
                break;
            case "phoneNumber": et = phoneNumberEditText;
                break;
            default: et = null;
                break;
        }
        if (et != null)
            buttonPressed(view.findViewWithTag(tag), et);

    }

    private void buttonPressed(Button button, EditText editText){
        if(!editText.isEnabled()){
            editText.setEnabled(true);
            editText.requestFocus();
            button.setText(R.string.apply_label);
        }else{
            editText.setEnabled(false);
            button.setText(R.string.edit_label);
            String str = editText.getText().toString();
            String tag = button.getTag().toString();
            user.setByFieldTag(tag, str);
            Log.d("USERNAME:::::", user.getUserName());
            changed = true;
        }
    }
}
