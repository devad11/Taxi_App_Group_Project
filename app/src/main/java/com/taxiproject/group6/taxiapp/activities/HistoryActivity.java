package com.taxiproject.group6.taxiapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    private static String uid;
    private static FirebaseUser firebaseUser;
    private ListView myListView;
    //private ArrayList<String> header = new ArrayList<>();
    private Button btnRefresh;
    private static List<String> headers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btnRefresh = (Button) findViewById(R.id.btnRefresh);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = Objects.requireNonNull(firebaseUser).getUid();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headers = DatabaseConnector.toHistory();


            }
        });

    }

    public static void update(List<String> keys){

        headers = keys;
        getRightKeys(headers);
    }

    private static void getRightKeys(List<String> headers) {

        List<String> userHistoryKeys = new ArrayList<>();

        for (int i = 0; i < headers.size(); i++) {
            String[] key = headers.get(i).split("\\+");
            if (key[0].equals(uid)){
                userHistoryKeys.add(headers.get(i));
            }
        }
        //System.out.println(userHistoryKeys);
        for (int i = 0; i < userHistoryKeys.size(); i++) {
            DatabaseConnector.getHistoryDataFromDatabase(userHistoryKeys.get(i));

        }

    }

}
