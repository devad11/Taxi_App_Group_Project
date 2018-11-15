package com.taxiproject.group6.taxiapp.activities;

import android.app.Activity;
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
import java.util.Map;

public class UserDetailsActivity extends AppCompatActivity {

    private LinearLayout userNameLayout, firstNameLayout, lastNameLayout, phoneNumberLayout;
    private Button saveButton, cancelButton;
    private Button userNameButton, firstNameButton, lastNameButton, phoneNumberButton;
    private EditText userNameEditText, firstNameEditText, lastNameEditText, phoneNumberEditText;
    private DatabaseConnector databaseConnector;
    private User user;
//    private Map<String, Object> userMap;
    private boolean changed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

//        databaseConnector = new DatabaseConnector();
        changed = false;
        user = DatabaseConnector.loadFromDatabase();
//        userMap = user.toMap();
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

        Log.d("USERNAME:oncreate():", user.getUserName());

        loadDataIntoTextFields();
    }

    private void loadDataIntoTextFields(){
        userNameEditText.setText(user.getUserName());
        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        phoneNumberEditText.setText(user.getPhoneNumber());
    }

    public void confirmButtonPressed(View view) {
        if(changed)
            DatabaseConnector.updateAllDetails(user.toMap());
        closeActivity();
    }

    public void cancelButtonPressed(View view) {
        closeActivity();
    }

    public void changeData(){

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
//        String str = editText.getText().toString();
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
//        return str;
    }
}
