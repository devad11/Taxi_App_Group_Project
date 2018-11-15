package com.taxiproject.group6.taxiapp.classes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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

public class DatabaseConnector {

    private static String uid;
    private static FirebaseUser firebaseUser;
    private static DatabaseReference ref;
    private static DatabaseReference usersRef;
    private static Map<String, Object> users;
    private static User newUser = new User();

    public static void loadToDatabase(User user){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null)
        {
            uid = firebaseUser.getUid();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users");
            usersRef = ref.child(uid);
//            user = LoginFragment.getUser();
            users = user.toMap();
//            users.put(user.getUserName(), user);

            usersRef.setValue(users);
//            ref.child(user.getUserName()).setValue(users);
        }
    }

    public static User loadFromDatabase(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/" + uid) ;

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newUser = dataSnapshot.getValue(User.class);
//                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//                    newUser = singleSnapshot.getValue(User.class);
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return newUser;
    }

    public static void updateDetails(String childName, String name){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/" + uid);
        ref.child(childName).setValue(name);
        newUser = loadFromDatabase();
    }

    public static void updateAllDetails(Map<String, Object> map){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/" + uid);
        ref.updateChildren(map);
        newUser = loadFromDatabase();
    }
}
