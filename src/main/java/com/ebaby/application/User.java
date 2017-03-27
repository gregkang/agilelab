package com.ebaby.application;

import java.util.Objects;

public class User {
    private String firstName;
    private String lastName;
    private String userEmail;
    private String userName;
    private String password;
    private boolean authenticated;
    private Role role;

    public enum Role {
        SELLER,
        BIDDER
    }

    public User(String firstName, String lastName, String userEmail, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.userName = userName;
        this.password = password;
        authenticated = false;
        role = Role.BIDDER;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean login(String password) {
        if (Objects.equals(this.password, password)) {
            authenticated = true;
            return true;
        }
        return false;
    }

    public void logout() {
        setAuthenticated(false);
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return authenticated == user.authenticated && Objects.equals(firstName, user.firstName) && Objects.equals(
                lastName,
                user.lastName) && Objects.equals(userEmail, user.userEmail) && Objects.equals(userName,
                user.userName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, userEmail, userName, password, authenticated);
    }
}