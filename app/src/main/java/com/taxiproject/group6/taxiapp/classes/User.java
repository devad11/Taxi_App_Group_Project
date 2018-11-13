package com.taxiproject.group6.taxiapp.classes;

public class User {

        public String dateOfBirth;
        public String fullName;
        public String nickName;
        public String phoneNumber;

        public User(){}

        public User(String fullName,String nickName, String phoneNumber, String dateOfBirth) {
            this.nickName = nickName;
            this.phoneNumber = phoneNumber;
            this.fullName = fullName;
            this.dateOfBirth = dateOfBirth;
        }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString(){
            return String.format("USER: %s %s %s %s ", this.fullName, this.nickName, this.dateOfBirth, this.phoneNumber);
    }
}
