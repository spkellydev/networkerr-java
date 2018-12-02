package com.networkerr.app.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSchema {
    long id;
    private  String email;
    private  String password;
    private String salt;
    @JsonCreator
    public UserSchema(@JsonProperty("email") String email, @JsonProperty("password") String password, @JsonProperty("salt") String salt) {
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

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

    public void setSalt(String salt) { this.salt = salt; }

    @JsonIgnore
    public String getSalt() { return this.salt; }
}
