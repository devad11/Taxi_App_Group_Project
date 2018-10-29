//package com.taxiproject.group6.taxiapp.classes;
//
//import android.location.Location;
//import android.location.LocationListener;
//import android.os.Bundle;
//import android.util.Log;
//
//public class LocationHelper implements LocationListener {
//
//    private static final String TAG = "LocationHelper";
//
//    private double latitude = 51;
//    private double longitude = -8;
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double logitude) {
//        this.longitude = logitude;
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        if(location != null){
//            this.latitude = location.getLatitude();
//            this.longitude = location.getLongitude();
//            Log.d(TAG, "Lat: " + this.latitude);
//            Log.d(TAG, "Lng: " + this.longitude);
//        }
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
//}
