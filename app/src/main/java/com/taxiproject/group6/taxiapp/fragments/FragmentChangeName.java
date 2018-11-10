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

        editTextFirstName = (EditText) getView().findViewById(R.id.editTextFirstName);
        editTextSurname = (EditText) getView().findViewById(R.id.editTextSurname);
        buttonSave = (Button) getView().findViewById(R.id.buttonSave);
        buttonCancel = (Button) getView().findViewById(R.id.buttonCancel);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = editTextFirstName.getText().toString() + " " + editTextSurname.getText().toString();
                PersonalDetailsActivity.loadToDatabase();
            }
        });
        return inflater.inflate(R.layout.fragment_change_name, container, false);
    }

    public static String getUserName(){
        return userName;
    }

}
