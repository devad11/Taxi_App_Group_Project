package com.taxiproject.group6.taxiapp.activities;

import android.app.ProgressDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.SectionsStatePagerAdapter;
import com.taxiproject.group6.taxiapp.fragments.LoginFragment;
import com.taxiproject.group6.taxiapp.fragments.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        setUpViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager vp){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Login Page");
        adapter.addFragment(new RegisterFragment(), "Register Page");
        vp.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNo){
        viewPager.setCurrentItem(fragmentNo);
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

}
