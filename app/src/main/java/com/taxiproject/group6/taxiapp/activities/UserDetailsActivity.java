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
    private Button saveButton, cancelButton;
    private Button userNameButton, firstNameButton, lastNameButton, phoneNumberButton;
    private EditText userNameEditText, firstNameEditText, lastNameEditText, phoneNumberEditText;
    private DatabaseConnector databaseConnector;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        databaseConnector = new DatabaseConnector();
        user = DatabaseConnector.loadFromDatabase();
        saveButton = findViewById(R.id.saveUserDetailsButton);
        cancelButton = findViewById(R.id.cancelButton);
        userNameButton = findViewById(R.id.userNameButton);
        userNameEditText = findViewById(R.id.userNameEditText);
        firstNameButton = findViewById(R.id.firstNameButton);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameButton = findViewById(R.id.lastNameButton);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        phoneNumberButton = findViewById(R.id.phoneNumberButton);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);


        loadDataIntoTextFields();
    }

    private void loadDataIntoTextFields(){
        userNameEditText.setText(user.getUserName());
    }

    public void confirmButtonPressed(View view) {
        DatabaseConnector.updateAllDetails(user);
    }

    public void backButtonPressed(View view) {
    }

    public void changeData(){

    }

    public void editButtonPressed(View view) {

        user.setUserName(buttonPressed(userNameButton, userNameEditText));
//        DatabaseConnector.updateDetails(userNameButton.getTag().toString(), user.getUserName());
//        userNameButton.setEnabled(true);

//        else if(!firstNameButton.isEnabled()){
//            user.setFirstName(buttonPressed(firstNameButton, firstNameEditText));
//            DatabaseConnector.updateDetails(firstNameButton.getTag().toString(), user.getFirstName());
//            firstNameButton.setEnabled(true);
//        }
//        else if(!lastNameButton.isEnabled()){
//            user.setLastName(buttonPressed(lastNameButton, lastNameEditText));
//            DatabaseConnector.updateDetails(lastNameButton.getTag().toString(), user.getLastName());
//            lastNameButton.setEnabled(true);
//        }
//        else if(!phoneNumberButton.isEnabled()){
//            user.setPhoneNumber(buttonPressed(phoneNumberButton, phoneNumberEditText));
//            DatabaseConnector.updateDetails(phoneNumberButton.getTag().toString(), user.getPhoneNumber());
//            phoneNumberButton.setEnabled(true);
//        }
    }

    public void cancelButtonPressed(View view) {
    }

    private String buttonPressed(Button button, EditText editText){
        String str = editText.getText().toString();
        if(!editText.isEnabled()){
            editText.setEnabled(true);
            editText.requestFocus();
            button.setText(R.string.apply_label);
        }else{
            editText.setEnabled(false);
            button.setText(R.string.edit_label);
            str = editText.getText().toString();
        }
        return str;
    }
}
