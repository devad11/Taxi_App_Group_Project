package com.taxiproject.group6.taxiapp.classes;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Destination {
    private String destinationName;
    private String lat;
    private String lng;
    private Map<String, Object> result;

    public Destination(){}

    public Destination(String destinationName, String lat, String lng) {
        this.destinationName = destinationName;
        this.lat = lat;
        this.lng = lng;
    }

    //--------------------------
    //      GETTERS
    //--------------------------
    public String getDestinationName() {
        return destinationName;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    //--------------------------
    //      SETTERS
    //--------------------------
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    //-------------------------
    //      METHODS
    //-------------------------
    @NonNull
    public String toString(){
        return String.format("Destination: %s %s %s ", this.destinationName, this.lat, this.lng);
    }

    public Map<String, Object> toMap() {
        result = new HashMap<>();
        result.put("uid", destinationName);
        result.put("firstName", this.lat);
        result.put("lastName", this.lng);

        return result;
    }
}


