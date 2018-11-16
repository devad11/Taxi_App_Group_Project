package com.taxiproject.group6.taxiapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.User;

public class UserDetailsActivity extends AppCompatActivity {

    private EditText userNameEditText, firstNameEditText, lastNameEditText, phoneNumberEditText;
    private User user;
    private boolean changed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        changed = false;
        user = DatabaseConnector.loadFromDatabase();

        userNameEditText = findViewById(R.id.userNameEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);

        Log.d("USERNAME:onCreate():", user.getUserName());

        init();
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
