package com.taxiproject.group6.taxiapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangeName extends Fragment {

    private static String userName;
    private String lastName;
    private EditText editTextFirstName, editTextSurname;
    private Button buttonSave, buttonCancel;


    public FragmentChangeName() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen


        View v = inflater.inflate(R.layout.fragment_change_name, container, false);
        editTextFirstName = (EditText) v.findViewById(R.id.editTextFirstName);
        editTextSurname = (EditText) v.findViewById(R.id.editTextSurname);
        buttonSave = (Button) v.findViewById(R.id.buttonSave);
        buttonCancel = (Button) v.findViewById(R.id.buttonCancel);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextSurname.getText().toString();
//                User user = LoginFragment.getUser();
                User user = DatabaseConnector.loadFromDatabase();
                Log.d("USER:::::", user.toString());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                DatabaseConnector.updateDetails(user.getNickName(), "firstName", firstName);
                DatabaseConnector.updateDetails(user.getNickName(), "lastName", lastName);
//                DatabaseConnector.loadToDatabase();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFirstName.getText().clear();
                editTextSurname.getText().clear();
            }
        });
        return v;
    }

    public static String getUserName(){
        return userName;
    }

}
