package com.taxiproject.group6.taxiapp.classes;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class PlaceInfo {

    private String name;
    private String address;
    private String phoneNo;
    private String id;
    private float rating;
    private Uri websiteUri;
    private LatLng latLng;
    private String position;

    public PlaceInfo() {
    }

    public PlaceInfo(String name, String address, String phoneNo, String id,
                     float rating, Uri websiteUri, LatLng latLng) {
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.id = id;
        this.rating = rating;
        this.websiteUri = websiteUri;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Uri getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUri = websiteUri;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "name='" + name +
                "\naddress='" + address +
                "\nphoneNo='" + phoneNo +
                "\nid='" + id +
                "\nrating=" + rating +
                "\nwebsiteUri=" + websiteUri +
                "\nlatLng=" + latLng +
                '}';
    }
}