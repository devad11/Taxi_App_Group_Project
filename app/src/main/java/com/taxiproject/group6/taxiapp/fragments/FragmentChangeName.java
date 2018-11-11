package com.taxiproject.group6.taxiapp.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.activities.PersonalDetailsActivity;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangeName extends Fragment {

    private static String userName;
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
                userName = editTextFirstName.getText().toString() + " " + editTextSurname.getText().toString();
                PersonalDetailsActivity.loadToDatabase();
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
