package com.inxs5859.liber.HelperClasses;

public class UserHelperClass {

    String fullName, userName, password, email, gender, date;

    public UserHelperClass(){}

    public UserHelperClass(String fullName, String userName, String password, String email, String gender, String date) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
