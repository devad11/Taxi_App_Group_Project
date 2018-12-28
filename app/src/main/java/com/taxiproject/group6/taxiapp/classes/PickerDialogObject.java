package com.taxiproject.group6.taxiapp.classes;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.activities.MapsActivity;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.taxiproject.group6.taxiapp.fragments.LoginFragment.newUser;

public class PickerDialogObject extends Dialog{

    private ListView listView;
    private ArrayList<String> myArrayList;
    private ArrayAdapter<String> adapter;

    private static String uid;
    private static FirebaseUser firebaseUser;
    private static DatabaseReference ref;
    private static DatabaseReference usersRef;
    private static Map<String, Object> destinations;
    private static Destination newDestination = new Destination();

    public PickerDialogObject(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_dialog);
        Destination myDestination = new Destination("TEST","12121212","65656565");
        loadToDatabase(myDestination);
        loadFromDatabase();
        System.out.println("?????????????????????????????????????????????????????????????????????????????????");
        System.out.println(Destination.class.toString());

//        listView = (ListView) findViewById(R.id.listView);
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/7vAxL2Plr4b79JG5mzs3q7SCehF2/Destinations");
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String name = ds.getKey();
//                    myArrayList = new ArrayList<>();
//                    myArrayList.add(name);
//                }
//
//
//                listView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        ref.addListenerForSingleValueEvent(eventListener);
    }

    public static void loadToDatabase(Destination destination){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = Objects.requireNonNull(firebaseUser).getUid();
        if (firebaseUser != null)
        {
            uid = firebaseUser.getUid();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users" + uid + "Destinations");
            usersRef = ref.child("TEST");
            destinations = destination.toMap();
            usersRef.setValue(destinations);
        }
    }

    public static Destination loadFromDatabase(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = Objects.requireNonNull(firebaseUser).getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://taxiapp-e3904.firebaseio.com/users/" + uid + "Destinations") ;

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                newDestination = dataSnapshot.getValue(Destination.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return newDestination;
    }
}