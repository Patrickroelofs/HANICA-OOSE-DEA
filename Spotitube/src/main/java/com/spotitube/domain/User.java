package com.spotitube.domain;

public class User {
    private String user;
    private String password;

    public User() {

    }

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
