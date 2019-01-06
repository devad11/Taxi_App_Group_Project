package com.taxiproject.group6.taxiapp.activities;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.auth.FirebaseAuth;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.NotificationHelper;

public class ReviewActivity extends AppCompatActivity {

    private SeekBar mySeekBar;
    private EditText reviewEditText;
    private TextView rateTextView;
    private Button reviewSendButton;
    private String userReview, userRate;
    NotificationHelper notificationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mySeekBar = findViewById(R.id.mySeekBar);
        reviewEditText = findViewById(R.id.reviewEditText);
        rateTextView = findViewById(R.id.rateTextView);
        reviewSendButton = findViewById(R.id.reviewSendButton);

        reviewSendButton.setOnClickListener(v -> {
            notificationHelper = new NotificationHelper(this);
            userReview = reviewEditText.getText().toString();
            DatabaseConnector.reviewToDatabase(userRate, userReview);
            NotificationCompat.Builder builder = notificationHelper.secondaryNotification(userRate, userReview);
            notificationHelper.getManager().notify(2, builder.build());
            Intent i = new Intent(ReviewActivity.this, MapsActivity.class);
            startActivity(i);
        });

        userRate = ("" + 5);
        mySeekBar.setProgress(4);

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                userRate = ("" + (progress + 1));
                rateTextView.setText(userRate);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
