package com.taxiproject.group6.taxiapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxiproject.group6.taxiapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangeEmail extends Fragment {


    public FragmentChangeEmail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_email, container, false);
    }

}
