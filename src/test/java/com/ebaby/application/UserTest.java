package com.ebaby.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertEquals(firstName, testUser.getFirstName());
    }

    @Test
    public void checkLastNAme() {
        assertEquals(lastName, testUser.getLastName());
    }

    @Test
    public void checkUserEmail() {
        assertEquals(userEmail, testUser.getUserEmail());
    }

    @Test
    public void checkUserName() {
        assertEquals(userName, testUser.getUserName());
    }

    @Test
    public void checkPassword() {
        assertEquals(password, testUser.getPassword());
    }

    @Test
    public void tryLoginSuccess() {
        assertTrue("Login must be successful", testUser.login(testUser.getPassword()));
    }

    @Test
    public void tryLoginFailureBadPassword() {
        assertFalse("Login must not be successful", testUser.login("badPassword"));
    }

    @Test
    public void tryLoginFailureBadUser() {
        assertFalse(testUser.login("bad password"));
    }

    @Test
    public void tryLogoutSuccess() {
        testUser.login(testUser.getPassword());
        testUser.logout();
        assertFalse("Logout must be successful", testUser.isAuthenticated());
    }

    @Test
    public void isAuthenticated() {
        assertFalse(testUser.isAuthenticated());
        testUser.login(testUser.getPassword());
        assertTrue(testUser.isAuthenticated());
    }

    @Test
    public void testRole() {
        assertEquals(User.Role.BIDDER, testUser.getRole());
        testUser.setRole(User.Role.SELLER);
        assertEquals(User.Role.SELLER, testUser.getRole());
    }
}