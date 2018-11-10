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
import com.taxiproject.group6.taxiapp.classes.SectionsStatePagerAdapter;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangeEmail;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangeName;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangePassword;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangePhoneNum;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PersonalDetailsActivity extends AppCompatActivity {

    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;

    private ProgressDialog progressDialog;
    private Button changeNameButton, changeEmailButton, changePhoneButton, changePasswordButton;
    private String uid, email, name;
    private FirebaseUser user;
    private DatabaseReference ref;


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

        user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .build();
        if (user != null)
        {
            email = user.getEmail();
            name = user.getDisplayName();


            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            uid = user.getUid();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/");
            DatabaseReference usersRef = ref.child("users");

            Map<String, User> users = new HashMap<>();
            users.put(uid, new User("June 23, 1912", "Alan Turing"));

            usersRef.setValue(users);
        }
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

    public static class User {

        public String date_of_birth;
        public String full_name;
        public String nickname;

        public User(String dateOfBirth, String fullName) {
            date_of_birth = dateOfBirth;
            full_name = fullName;
        }

        public User(String dateOfBirth, String fullName, String nickname) {
            // ...
        }

    }

}

