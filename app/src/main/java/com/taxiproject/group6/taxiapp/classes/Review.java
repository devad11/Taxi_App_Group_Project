package com.taxiproject.group6.taxiapp.classes;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Review {

    private int rating;
    private String message;
    private String userUid;

    public Review() {
    }

    public Review(int rating, String message) {
        this.rating = rating;
        this.message = message;
    }

    public Review(int rating, String message, String userUid) {
        this.rating = rating;
        this.message = message;
        this.userUid = userUid;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("rating", this.rating);
        result.put("message", this.message);
        result.put("userUid", this.userUid);

        return result;
    }
    @NonNull
    @Override
    public String toString() {
        return "Review{" +
                "rating=" + rating +
                ", message=" + message +
                ", userUid=" + userUid +
                '}';
    }
}
