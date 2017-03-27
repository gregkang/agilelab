package com.ebaby.application;

public class User {
    private String firstName;

    public User(String firstName, String lastName, String userEmail, String userName, String password) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
