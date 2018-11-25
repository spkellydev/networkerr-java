package com.networkerr.app.models;

public class UserSchema {
    long id;
    private static String email;
    private static String password;
    private static UserSchema instance;
    private UserSchema() {
        instance = new UserSchema();
    }

    public static UserSchema initialize(String email, String password) {
        setEmail(email);
        setPassword(password);
        return instance;
    }

    public static void setEmail(String userEmail) {
        email = userEmail;
    }

    public static void setPassword(String userPassword) {
        password = userPassword;
    }
}
