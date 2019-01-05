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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    private static String uid;
    private static FirebaseUser firebaseUser;
    private ListView myListView;
    private static ArrayList<String> data = new ArrayList<>();
    private static ArrayList<String> time = new ArrayList<>();
    private static ArrayList<String> total = new ArrayList<>();
    private Button btnRefresh;
    private static List<String> headers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        myListView = (ListView) findViewById(R.id.myListView);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = Objects.requireNonNull(firebaseUser).getUid();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headers = DatabaseConnector.toHistory();
                if (data.size() > 0) {
                    for (int i = 0; i < time.size(); i++) {
                       String dataAndTime = data.get(i) + " " + time.get(i);
                       total.addAll(Arrays.asList(dataAndTime));
                    }
                    System.out.println(total);
                    toListView(total);
                }
            }
        });

    }

    private void toListView(ArrayList<String> total) {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,total);
        myListView.setAdapter(arrayAdapter);
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
                time.add(key[1]);
                userHistoryKeys.add(headers.get(i));

            }
        }
        //System.out.println(userHistoryKeys);
        for (int i = 0; i < userHistoryKeys.size(); i++) {
            DatabaseConnector.getHistoryDataFromDatabase(userHistoryKeys.get(i));

        }

    }

    public static void loadToList(String[] values) {
        data.addAll(Arrays.asList(values));
        System.out.println("data: " + data);
    }

}
