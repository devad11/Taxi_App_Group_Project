package com.taxiproject.group6.taxiapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.activities.PersonalDetailsActivity;
import com.taxiproject.group6.taxiapp.classes.LoadToDatabase;
import com.taxiproject.group6.taxiapp.classes.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangePhoneNum extends Fragment {

    private static String phNum;
    private EditText editTextPhone;
    private Button buttonSavePhone, buttonCancelPhone;

    public FragmentChangePhoneNum() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_phone_num, container, false);

        editTextPhone = (EditText) v.findViewById(R.id.editTextSurname);
        buttonSavePhone = (Button) v.findViewById(R.id.buttonSavePhone);
        buttonCancelPhone = (Button) v.findViewById(R.id.buttonCancelPhone);


        buttonSavePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phNum = editTextPhone.getText().toString();
                User user = LoginFragment.getUser();
                user.setFullName(phNum);
                LoadToDatabase.loadToDatabase();
            }
        });
        buttonCancelPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPhone.getText().clear();
            }
        });

        return v;
    }
    public static String getUserPhoneNumber(){
        return phNum;
    }

}
