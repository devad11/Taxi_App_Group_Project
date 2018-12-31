package com.taxiproject.group6.taxiapp.activities;

import android.content.Intent;
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

public class ReviewActivity extends AppCompatActivity {

    private SeekBar mySeekBar;
    private EditText reviewEditText;
    private TextView rateTextView;
    private Button reviewSendButton;
    private String userReview, userRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mySeekBar = (SeekBar) findViewById(R.id.mySeekBar);
        reviewEditText = (EditText) findViewById(R.id.reviewEditText);
        rateTextView = (TextView) findViewById(R.id.rateTextView);
        reviewSendButton = (Button) findViewById(R.id.reviewSendButton);

        reviewSendButton.setOnClickListener(v -> {
            DatabaseConnector.reviewToDatabase(userRate, reviewEditText.getText().toString());
            Intent i = new Intent(ReviewActivity.this, MapsActivity.class);
            startActivity(i);
        });

        mySeekBar.setProgress(4);

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rateTextView.setText("" + (progress + 1));
                userRate = ("" + (progress + 1));
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
