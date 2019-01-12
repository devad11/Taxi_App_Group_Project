package com.taxiproject.group6.taxiapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.Review;
import com.taxiproject.group6.taxiapp.classes.ReviewsCustomAdapter;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewReviewsActivity extends AppCompatActivity {

    private final static String TAG = "ViewReviewsActivity";
    private ListView myListView;
    private ArrayList<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        Log.d(TAG, "onCreate()");

        Button closeButton = findViewById(R.id.closeButtonReview);
        Button refreshButton = findViewById(R.id.refreshButtonReview);
        myListView = findViewById(R.id.reviewsListView);

        reviews = DatabaseConnector.getReviewsFromDatabase();

        Log.d(TAG, "Review list size: " + reviews.size());

        closeButton.setOnClickListener(v -> finish());
        refreshButton.setOnClickListener(v -> {if(reviews != null) toListView();});
    }

    private void toListView() {
        myListView.setAdapter(null);
        ReviewsCustomAdapter customAdapter = new ReviewsCustomAdapter(this, R.layout.listview_review_layout, reviews);
        myListView.setAdapter(customAdapter);
    }
}
