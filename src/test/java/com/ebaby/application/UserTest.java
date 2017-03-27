package com.ebaby.application;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private String firstName = "firstName";
    private String lastName = "lastName";
    private String userEmail = "userEmail";
    private String userName = "userName";
    private String password = "password";
    private User testUser;

    @Before
    public void setup() {
        testUser = new User(firstName, lastName, userEmail, userName, password);
    }

    @Test
    public void checkFirstName() {
        Assert.assertEquals(firstName, testUser.getFirstName());
    }

    @Test
    public void checkLastNAme(){
        Assert.assertEquals(lastName, testUser.getLastName());
    }

    @Test
    public void checkUserEmail(){
        Assert.assertEquals(userEmail, testUser.getUserEmail());
    }

    @Test
    public void checkUserName(){
        Assert.assertEquals(userName, testUser.getUserName());
    }

    @Test
    public void checkPassword(){
        Assert.assertEquals(password, testUser.getPassword());
    }

    @Test
    public void tryRegistering(){
        Assert.assertTrue("Register returned false", testUser.doRegister());
    }

    @Test
    public void tryLoginSuccess(){
        Assert.assertTrue("Login must be successful", testUser.login());
    }

    @Test
    public void tryLogoutSuccess(){
        Assert.assertTrue("Logout must be successful",testUser.logout());
    }
}