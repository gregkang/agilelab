package com.ebaby.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Users {
    private List<User> userList;

    public Users() {
        userList = new ArrayList<>();
    }

    public User findByUserName(String userName) {
        for (User user : userList) {
            if (Objects.equals(user.getUserName(), userName)) {
                return user;
            }
        }
        return null;
    }

    public void register(User user) {
        userList.add(user);
    }

    public boolean login(String userName, String password) {
        User user = findByUserName(userName);
        if (user != null) {
            return user.login(password);
        }
        return false;
    }

    public boolean logout(String userName) {
        User user = findByUserName(userName);
        if (user != null) {
            user.logout();
            return true;
        }
        return false;
    }
}
