package com.taxiproject.group6.taxiapp.classes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DatabaseConnector {

    private static final String TAG = "DatabaseConnector";

    private static String databaseHeader;
    private static FirebaseUser firebaseUser;
    private static DatabaseReference ref;
    private static DatabaseReference usersRef;
    private static Map<String, Object> users;
    private static User newUser = new User();


    public static void sendBooking(String locLat, String locLng, String destLat, String destLng, String cost){

        Date currentTime = Calendar.getInstance().getTime();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null)
        {
            databaseHeader = firebaseUser.getUid();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/bookings");
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/time");
            usersRef.setValue(formatTime.format(currentTime));
            SimpleDateFormat formatDate = new SimpleDateFormat("MM");
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/date");
            usersRef.setValue(formatDate.format(currentTime));
            SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/year");
            usersRef.setValue(formatYear.format(currentTime));
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/locLat");
            usersRef.setValue(locLat);
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/locLng");
            usersRef.setValue(locLng);
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/destLat");
            usersRef.setValue(destLat);
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/destLng");
            usersRef.setValue(destLng);
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/cost");
            usersRef.setValue(cost);
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/user");
            usersRef.setValue(databaseHeader);
            
        }
    }

    public static void reviewToDatabase(String rate, String review){

        Date currentTime = Calendar.getInstance().getTime();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null)
        {
            databaseHeader = firebaseUser.getUid();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/reviews");
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/rate");
            usersRef.setValue(rate);
            usersRef = ref.child(databaseHeader + "/" + currentTime.toString() + "/review");
            usersRef.setValue(review);
        }
    }

    public static void loadToDatabase(User user){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null)
        {
            databaseHeader = firebaseUser.getUid();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users");
            usersRef = ref.child(databaseHeader);
            users = user.toMap();
            usersRef.setValue(users);
        }
    }

    public static User loadFromDatabase(){
        if(firebaseUser == null || !firebaseUser.equals(FirebaseAuth.getInstance().getCurrentUser()))
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseHeader = Objects.requireNonNull(firebaseUser).getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/" + databaseHeader) ;

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                newUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        Log.d(TAG, newUser.toString());
        return newUser;
    }

    public static void updateDetails(String childName, String name){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseHeader = Objects.requireNonNull(firebaseUser).getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/" + databaseHeader);
        ref.child(childName).setValue(name);
    }

    public static void updateAllDetails(User user){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseHeader = Objects.requireNonNull(firebaseUser).getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/" + databaseHeader);
        ref.updateChildren(user.toMap());
        newUser = user;
    }
}
