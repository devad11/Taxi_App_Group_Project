package com.taxiproject.group6.taxiapp.classes;

public class User {

        public String date_of_birth;
        public String full_name;
        public String nickname;
        public String phone_number;

        public User(){}

        public User(String fullName,String nickName, String phoneNumber, String dateOfBirth) {
            nickname = nickName;
            phone_number = phoneNumber;
            full_name = fullName;
            date_of_birth = dateOfBirth;
        }

    public String getDateOfBirth() {
        return date_of_birth;
    }

    public void setDateOfBirth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }
}
