package com.taxiproject.group6.taxiapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ListView myListView;
    private ArrayList<String> header = new ArrayList<>();
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("bookings");

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseConnector.toHistory();
            }
        });

    }
}
