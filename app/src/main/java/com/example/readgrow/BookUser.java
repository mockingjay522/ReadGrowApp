package com.example.readgrow;

public class BookUser {
    private String fName;
    private String email;
    private String username;
    private String password;
    private int age;
    private String address;
    private String postalCode;
    private String userID;

    public BookUser(String fName, String email, String username, String password, int age, String address, String postalCode, String userID) {
        this.fName = fName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.age = age;
        this.address = address;
        this.postalCode = postalCode;
        this.userID = userID;
    }

    public BookUser(String fName, int age,  String address , String postalCode, String email, String password) {
        this.fName = fName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
    }


    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BookUser(String userID, String fName, String email, String password, int age, String address, String postalCode) {
        this.userID = userID;
        this.fName = fName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.address = address;
        this.postalCode = postalCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
