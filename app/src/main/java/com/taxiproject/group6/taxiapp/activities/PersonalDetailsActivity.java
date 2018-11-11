package com.taxiproject.group6.taxiapp.activities;


import android.app.ProgressDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.LoadToDatabase;
import com.taxiproject.group6.taxiapp.classes.SectionsStatePagerAdapter;
import com.taxiproject.group6.taxiapp.classes.User;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangeEmail;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangeName;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangePassword;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangePhoneNum;
import com.taxiproject.group6.taxiapp.fragments.RegisterFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PersonalDetailsActivity extends AppCompatActivity {

    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;

    private ProgressDialog progressDialog;
    private Button changeNameButton, changeEmailButton, changePhoneButton, changePasswordButton;
    private static String uid;
    private String email;
    private String name;
    private static FirebaseUser user;
    private static DatabaseReference ref;
    public static DatabaseReference usersRef;
    public static Map<String, User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        changeNameButton = findViewById(R.id.changeNameButton);
        changeEmailButton = findViewById(R.id.changeEmailButton);
        changePhoneButton = findViewById(R.id.changePhoneButton);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        progressDialog = new ProgressDialog(this);

        setUpViewPager(viewPager);

        changeNameButton.setOnClickListener(v -> showNameChange());
        changeEmailButton.setOnClickListener(v -> showEmailChange());
        changePhoneButton.setOnClickListener(v -> showPhoneChange());
        changePasswordButton.setOnClickListener(v -> showPasswordChange());

        LoadToDatabase.loadFromDatabase();
    }

    private void setUpViewPager(ViewPager vp){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentChangeEmail(), "Change Email");
        adapter.addFragment(new FragmentChangeName(), "Change Name");
        adapter.addFragment(new FragmentChangePassword(), "Change Password");
        adapter.addFragment(new FragmentChangePhoneNum(), "Change Phone Number");
        vp.setAdapter(adapter);
    }

    private void showNameChange(){
        ((PersonalDetailsActivity)Objects.requireNonNull(PersonalDetailsActivity.this)).setViewPager(1);
    }
    private void showEmailChange(){
        ((PersonalDetailsActivity)Objects.requireNonNull(PersonalDetailsActivity.this)).setViewPager(0);
    }
    private void showPhoneChange(){
        ((PersonalDetailsActivity)Objects.requireNonNull(PersonalDetailsActivity.this)).setViewPager(3);
    }
    private void showPasswordChange(){
        ((PersonalDetailsActivity)Objects.requireNonNull(PersonalDetailsActivity.this)).setViewPager(2);
    }

    public void setViewPager(int fragmentNo){
        viewPager.setCurrentItem(fragmentNo);
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }



}

