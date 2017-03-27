package com.ebaby.application;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private String firstName = "firstName";
    private User testUser;

    @Before
    public void setup() {
        testUser = new User(firstName, "lastName", "userEmail", "userName", "password");
    }

    @Test
    public void createUser() {
        Assert.assertEquals(firstName, testUser.getFirstName());
    }
}