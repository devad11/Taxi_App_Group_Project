package com.taxiproject.group6.taxiapp.classes;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String uid;
    private String firstName;
    private String lastName;
    private String nickName;
    private String emailAddress;
    private String dateOfBirth;
    private String phoneNumber;

    public User(){}

    public User(String firstName,String nickName, String phoneNumber, String dateOfBirth) {
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
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

    public String getNickName() {
        return nickName;
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

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
    public String toString(){
            return String.format("USER: %s %s %s %s ", this.firstName, this.nickName, this.dateOfBirth, this.phoneNumber);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("firstName", this.firstName);
        result.put("lastName", this.lastName);
        result.put("email", this.emailAddress);
        result.put("nickName", this.nickName);
        result.put("phoneNumber", this.phoneNumber);
        result.put("dateOfBirth", this.dateOfBirth);

        return result;
    }
}
