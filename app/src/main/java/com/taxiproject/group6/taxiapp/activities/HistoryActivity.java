package com.taxiproject.group6.taxiapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.CustomAdapter;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.JourneyDetails;
import com.taxiproject.group6.taxiapp.classes.User;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {


    private static final String TAG = "HistoryActivity";
    private ListView myListView;
    private ArrayList<JourneyDetails> journeyDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Log.d(TAG, "onCreate()");

        Button btnRefresh = findViewById(R.id.btnRefresh);
        myListView = findViewById(R.id.myListView);

        User user = DatabaseConnector.loadFromDatabase();
        journeyDetails = user.getHistory();
        Log.d(TAG, "SIZE OF ARRAY:"+ journeyDetails.size());
        if(journeyDetails != null)
            toListView();
        btnRefresh.setOnClickListener(v -> finish());
    }

    private void toListView() {
        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.history_listview_layout, journeyDetails);
        myListView.setAdapter(customAdapter);
    }

}
