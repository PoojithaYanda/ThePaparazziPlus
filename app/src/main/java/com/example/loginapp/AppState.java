package com.example.loginapp;

import com.example.loginapp.model.User;

public class AppState {

    private static User userInstance = null;

    public static void setUserInstance(User user) {
        userInstance = user;
    }

    public static User getUserInstance()
    {
        return userInstance;
    }
}
