package com.taxiproject.group6.taxiapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.CustomAdapter;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.JourneyDetails;
import com.taxiproject.group6.taxiapp.classes.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {


    private static final String TAG = "HistoryActivity";
    private ListView myListView;
    private Button btnRefresh;
    private ArrayList<JourneyDetails> journeyDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Log.d(TAG, "onCreate()");

        btnRefresh = findViewById(R.id.btnRefresh);
        myListView = findViewById(R.id.myListView);

        User user = DatabaseConnector.loadFromDatabase();
        journeyDetails = user.getHistory();
        Log.d(TAG, "-------------------------------SIZE OF ARRAY:"+ journeyDetails.size());
        if(journeyDetails != null)
            toListView();
        btnRefresh.setOnClickListener(v -> {
//            headers = DatabaseConnector.toHistory();
//            if (data.size() > 0) {
//                for (int i = 0; i < time.size(); i++) {
//                   String dataAndTime = data.get(i) + " " + time.get(i);
//                   total.addAll(Arrays.asList(dataAndTime));
//                }
//                System.out.println(total);
//                toListView(total);
//            }
        });

    }

    private void toListView() {
        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.history_listview_layout, journeyDetails);
        myListView.setAdapter(customAdapter);
    }

}
