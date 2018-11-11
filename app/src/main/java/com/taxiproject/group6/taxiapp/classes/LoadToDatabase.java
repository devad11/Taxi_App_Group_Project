package com.taxiproject.group6.taxiapp.classes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.taxiproject.group6.taxiapp.fragments.FragmentChangePhoneNum;
import com.taxiproject.group6.taxiapp.fragments.LoginFragment;
import com.taxiproject.group6.taxiapp.fragments.RegisterFragment;

import java.util.HashMap;
import java.util.Map;

public class LoadToDatabase {

    private static String uid;
    private static FirebaseUser user;
    private static DatabaseReference ref;
    public static DatabaseReference usersRef;
    public static Map<String, User> users;
    static User newUser = new User();

    public static void loadToDatabase(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            uid = user.getUid();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users");
            usersRef = ref.child(uid);
            User user = LoginFragment.getUser();
            users = new HashMap<>();
            System.out.println(FragmentChangePhoneNum.getUserPhoneNumber());
            users.put(user.getNickname(), user);

            usersRef.setValue(users);
        }
    }

    public static User loadFromDatabase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/" + uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return newUser;
    }
}
