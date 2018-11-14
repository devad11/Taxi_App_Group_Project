package com.taxiproject.group6.taxiapp.classes;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String uid;
    private String firstName;
    private String lastName;
    private String userName;
    private String emailAddress;
    private String dateOfBirth;
    private String phoneNumber;

    public User(){}

    public User(String uid, String firstName, String lastName, String userName, String emailAddress, String dateOfBirth, String phoneNumber) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    //--------------------------
    //      GETTERS
    //--------------------------
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUid() {
        return uid;
    }
    public String getEmailAddress() {
        return emailAddress;
    }

    //--------------------------
    //      SETTERS
    //--------------------------
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    //-------------------------
    //      METHODS
    //-------------------------
    @NonNull
    public String toString(){
            return String.format("USER: %s %s %s %s ", this.firstName, this.userName, this.dateOfBirth, this.phoneNumber);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("firstName", this.firstName);
        result.put("lastName", this.lastName);
        result.put("email", this.emailAddress);
        result.put("userName", this.userName);
        result.put("phoneNumber", this.phoneNumber);
        result.put("dateOfBirth", this.dateOfBirth);

        return result;
    }
}
