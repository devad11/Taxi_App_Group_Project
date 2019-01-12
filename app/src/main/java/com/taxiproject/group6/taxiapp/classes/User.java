package com.taxiproject.group6.taxiapp.classes;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User  {

    private String firstName;
    private String lastName;
    private String userName;
    private String dateOfBirth;
    private String phoneNumber;
    private String cardNo;
    private String expiryDate;
    private Map<String, Object> result;
    private ArrayList<JourneyDetails> history;

    public User(){}

    public User(String firstName, String lastName, String userName, String dateOfBirth,
                String phoneNumber, String cardNo, String expiryDate, ArrayList<JourneyDetails> history) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.cardNo = cardNo;
        this.expiryDate = expiryDate;
        this.history = history;
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

    public String getCardNo() {
        return cardNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public ArrayList<JourneyDetails> getHistory() {
        return history;
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

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setHistory(ArrayList<JourneyDetails> history) {
        this.history = history;
    }

    //-------------------------
    //      METHODS
    //-------------------------
    void addToHistory(JourneyDetails journeyDetails){
        if(this.history == null)
            this.history = new ArrayList<>();
        this.history.add(journeyDetails);
    }

    @NonNull
    public String toString(){
            return String.format("USER: %s %s %s %s ", this.firstName, this.userName, this.dateOfBirth, this.phoneNumber);
    }

    Map<String, Object> toMap() {
        result = new HashMap<>();
        result.put("firstName", this.firstName);
        result.put("lastName", this.lastName);
        result.put("userName", this.userName);
        result.put("phoneNumber", this.phoneNumber);
        result.put("dateOfBirth", this.dateOfBirth);
        result.put("cardNo", Encryption.encryptCardNumber(this.cardNo));
        result.put("expiryDate", this.expiryDate);
        result.put("history", this.history);

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
            case "cardNo": this.cardNo = value;
                break;
            case "expiryDate": this.expiryDate = value;
                break;
        }
//        result.put(tag, value);
        result = this.toMap();
    }
}
