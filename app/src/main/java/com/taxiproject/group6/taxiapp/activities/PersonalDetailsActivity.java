package com.taxiproject.group6.taxiapp.activities;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.SectionsStatePagerAdapter;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangeEmail;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangeName;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangePassword;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangePhoneNum;

public class PersonalDetailsActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        viewPager = findViewById(R.id.container);
        Button changeNameButton = findViewById(R.id.changeNameButton);
        Button changeEmailButton = findViewById(R.id.changeEmailButton);
        Button changePhoneButton = findViewById(R.id.changePhoneButton);
        Button changePasswordButton = findViewById(R.id.changePasswordButton);

        setUpViewPager(viewPager);

        changeNameButton.setOnClickListener(v -> showNameChange());
        changeEmailButton.setOnClickListener(v -> showEmailChange());
        changePhoneButton.setOnClickListener(v -> showPhoneChange());
        changePasswordButton.setOnClickListener(v -> showPasswordChange());

        DatabaseConnector.loadFromDatabase();
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
        PersonalDetailsActivity.this.setViewPager(1);
    }
    private void showEmailChange(){
        PersonalDetailsActivity.this.setViewPager(0);
    }
    private void showPhoneChange(){
        PersonalDetailsActivity.this.setViewPager(3);
    }
    private void showPasswordChange(){
        PersonalDetailsActivity.this.setViewPager(2);
    }

    public void setViewPager(int fragmentNo){
        viewPager.setCurrentItem(fragmentNo);
    }

}

