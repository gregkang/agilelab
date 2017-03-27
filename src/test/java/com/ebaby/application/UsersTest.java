package com.ebaby.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UsersTest {
    private Users users;
    private String firstName1 = "firstName1";
    private String lastName1 = "lastName1";
    private String userEmail1 = "userEmail1";
    private String userName1 = "userName1";
    private String password1 = "password1";
    private String firstName2 = "firstName2";
    private String lastName2 = "lastName2";
    private String userEmail2 = "userEmail2";
    private String userName2 = "userName2";
    private String password2 = "password2";
    private User user1;
    private User user2;

    @Before
    public void setup() {
        user1 = new User(firstName1, lastName1, userEmail1, userName1, password1);
        user2 = new User(firstName2, lastName2, userEmail2, userName2, password2);

        users = new Users();
        users.register(user1);
        users.register(user2);
    }

    @Test
    public void testRegister() {
        Users testUsers = new Users();
        assertNull(testUsers.findByUserName(user1.getUserName()));
        testUsers.register(user1);
        assertNotNull(testUsers.findByUserName(user1.getUserName()));
    }

    @Test
    public void testLoginPass() {
        assertTrue(users.login("userName1", "password1"));
        assertTrue(users.findByUserName("userName1").isAuthenticated());
    }

    @Test
    public void tryLoginSuccess() {
        Assert.assertTrue("Login must be successful", users.login(user1.getUserName(), user1.getPassword()));
    }

    @Test
    public void tryLoginFailureBadPassword() {
        assertFalse("Login must not be successful", users.login(user2.getUserName(), "badPassword"));
    }

    @Test
    public void tryLoginSFailureBadUser() {
        assertFalse("Login must be successful", users.login("somethinggoeshere", user2.getPassword()));
    }

    @Test
    public void testFindByUserName() {
        User actualUser = users.findByUserName(user1.getUserName());
        assertEquals(user1, actualUser);
    }

    @Test
    public void testFindByUserNameInvalidUser() {
        User actualUser = users.findByUserName("unknown user");
        assertNull(actualUser);
    }

    @Test
    public void tryLogoutSuccess() {
        assertTrue("Logout must be successful", users.logout(user1.getUserName()));
    }

    @Test
    public void tryLogoutInvalidUser() {
        assertFalse("Logout must be false for unknown username", users.logout("invalid username"));
    }
}
