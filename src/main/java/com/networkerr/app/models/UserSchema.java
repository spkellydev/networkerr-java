package com.networkerr.app.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSchema {
    long id;
    private  String email;
    private  String password;
    @JsonCreator
    public UserSchema(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String userEmail) {
        this.email = userEmail;
    }

    public void setPassword(String userPassword) {
        this.password = userPassword;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
