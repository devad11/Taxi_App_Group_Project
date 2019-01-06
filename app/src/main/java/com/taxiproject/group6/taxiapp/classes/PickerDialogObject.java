package com.taxiproject.group6.taxiapp.classes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.taxiproject.group6.taxiapp.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class PickerDialogObject extends Dialog{

    private static String uid;
    private static FirebaseUser firebaseUser;
    private static Address newAddress;

    public PickerDialogObject(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_dialog);
        Address myAddress = new Address("TEST","12121212","65656565");
        loadToDatabase(myAddress);
        loadFromDatabase();
        System.out.println("?????????????????????????????????????????????????????????????????????????????????");
        System.out.println(Address.class.toString());

    }

    private static void loadToDatabase(Address address){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = Objects.requireNonNull(firebaseUser).getUid();
        if (firebaseUser != null)
        {
            uid = firebaseUser.getUid();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users" + uid + "Address");
            DatabaseReference usersRef = ref.child(uid);
            Map<String, Object> destinations = address.toMap();
            usersRef.setValue(destinations);
        }
    }

    private static Address loadFromDatabase(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = Objects.requireNonNull(firebaseUser).getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/" + uid + "Destinations") ;

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                newAddress = dataSnapshot.getValue(Address.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return newAddress;
    }
}