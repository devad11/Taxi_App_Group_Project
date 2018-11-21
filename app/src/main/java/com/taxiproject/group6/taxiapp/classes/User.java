package com.taxiproject.group6.taxiapp.classes;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String firstName;
    private String lastName;
    private String userName;
    private String dateOfBirth;
    private String phoneNumber;
    private Map<String, Object> result;


    public User(){}

    public User(String firstName, String lastName, String userName, String dateOfBirth, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    //--------------------------
    //      GETTERS
    //--------------------------
    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    //--------------------------
    //      SETTERS
    //--------------------------
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }



    //-------------------------
    //      METHODS
    //-------------------------
    @NonNull
    public String toString(){
            return String.format("USER: %s %s %s %s ", this.firstName, this.userName, this.dateOfBirth, this.phoneNumber);
    }

    public Map<String, Object> toMap() {
        result = new HashMap<>();
        result.put("firstName", this.firstName);
        result.put("lastName", this.lastName);
        result.put("userName", this.userName);
        result.put("phoneNumber", this.phoneNumber);
        result.put("dateOfBirth", this.dateOfBirth);

        return result;
    }

    public void setByFieldTag(String tag, String value){
        switch (tag){
            case "userName":
                this.userName = value;
                break;
            case "firstName": this.firstName = value;
                break;
            case "lastName": this.lastName = value;
                break;
            case "phoneNumber": this.phoneNumber = value;
                break;
            case "dateOfBirth": this.dateOfBirth = value;
                break;
        }
//        result.put(tag, value);
        result = this.toMap();
    }
}
