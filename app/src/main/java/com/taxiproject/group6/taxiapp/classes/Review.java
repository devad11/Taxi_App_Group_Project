package com.taxiproject.group6.taxiapp.classes;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Review {

    static final int MAX_RATING = 5;
    private int rating;
    private String comment;

    public Review() {
    }

    public Review(int rating, String message) {
        this.rating = rating;
        this.comment = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("rating", this.rating);
        result.put("comment", this.comment);

        return result;
    }
    @NonNull
    @Override
    public String toString() {
        return "Review{" +
                "rating=" + rating +
                ", comment=" + comment +
                '}';
    }

}
