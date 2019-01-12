package com.taxiproject.group6.taxiapp.classes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.taxiproject.group6.taxiapp.activities.ViewReviewsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseConnector {

    private static final String TAG = "DatabaseConnector";

    private static String databaseHeader;
    private static FirebaseUser firebaseUser;
    private static DatabaseReference ref;
    private static DatabaseReference usersRef;
    private static Map<String, Object> users;
    private static User newUser = new User();
    private static ArrayList<Review> reviews;

    public static void sendBooking(JourneyDetails journeyDetails){

        Date currentTime = Calendar.getInstance().getTime();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null)
        {
            databaseHeader = firebaseUser.getUid();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/" + databaseHeader);
            SimpleDateFormat formatId = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss", Locale.UK);

            usersRef = ref.child("/history");
            Log.d(TAG, "Time: " + formatId.format(currentTime) + " + " + formatTime.format(currentTime));
            usersRef = usersRef.child("/" + formatId.format(currentTime) + " + " + formatTime.format(currentTime));
            journeyDetails.setDate(formatId.format(currentTime));
            journeyDetails.setTime(formatTime.format(currentTime));

            newUser.addToHistory(journeyDetails);

            usersRef.updateChildren(journeyDetails.toMap());
        }
    }

    public static void reviewToDatabase(Review review){

        Date currentTime = Calendar.getInstance().getTime();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/reviews");

        usersRef = ref.child(currentTime.toString());
        usersRef.updateChildren(review.toMap());

    }

    public static ArrayList<Review> getReviewsFromDatabase(){
        reviews = new ArrayList<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/reviews");

        Log.d(TAG, "----BEFORE----");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Review review = child.getValue(Review.class);
                    reviews.add(review);
                    Log.d(TAG, "Review: " + review);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d(TAG, "Review list size: " + reviews.size());
        return reviews;
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
//                newUser = dataSnapshot.getValue(User.class);
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d(TAG, "--------------------------------------------------" + ds.getKey() + ", "+  Objects.requireNonNull(ds.getValue()).toString());
                    newUser.setByFieldTag(ds.getKey(), Objects.requireNonNull(ds.getValue()).toString());
                    if(Objects.requireNonNull(ds.getKey()).equalsIgnoreCase("history")){
                        int i = 0;
                        ArrayList<JourneyDetails> journeyDetailsArrayList = new ArrayList<>();
                        for(DataSnapshot dsi : ds.getChildren()){
                            Log.d(TAG,i++ + " : " +  Objects.requireNonNull(dsi.getValue(JourneyDetails.class)).toString());
                            journeyDetailsArrayList.add(dsi.getValue(JourneyDetails.class));
                        }
                        newUser.setHistory(journeyDetailsArrayList);
                    }
                }
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
