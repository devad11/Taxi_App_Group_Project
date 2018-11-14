package com.taxiproject.group6.taxiapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.User;

import java.lang.reflect.Type;

public class UserDetailsActivity extends AppCompatActivity {

    private LinearLayout userNameLayout, firstNameLayout, lastNameLayout, phoneNumberLayout;
    private Button saveButton, backButton;
    private Button userNameButton;
    private EditText userNameEditText;
    private DatabaseConnector databaseConnector;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        databaseConnector = new DatabaseConnector();
        user = DatabaseConnector.loadFromDatabase();
        saveButton = findViewById(R.id.saveUserDetailsButton);
        backButton = findViewById(R.id.backButton);
        userNameButton = findViewById(R.id.userNameButton);
        userNameEditText = findViewById(R.id.userNameEditText);

        loadDataIntoTextFields();
    }

    private void loadDataIntoTextFields(){
        userNameEditText.setText(user.getUserName());
    }
}
