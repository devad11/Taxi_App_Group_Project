package com.taxiproject.group6.taxiapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.SectionsStatePagerAdapter;
import com.taxiproject.group6.taxiapp.fragments.LoginFragment;
import com.taxiproject.group6.taxiapp.fragments.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.container);

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
