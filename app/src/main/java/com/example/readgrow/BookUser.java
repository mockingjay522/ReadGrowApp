package com.example.readgrow;

public class BookUser {
    private String fName;
    private String email;
    private String password;
    private int age;
    private String address;



    public BookUser(String fName, String email, String password, int age, String address) {
        this.fName = fName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.address = address;
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

}
