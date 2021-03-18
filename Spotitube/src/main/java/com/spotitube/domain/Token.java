package com.spotitube.domain;

import java.util.UUID;

public class Token {
    private String user;
    private String token;

    public Token() {

    }

    public Token(String user, String token) {
        this.user = user;
        this.token = token;
    }

    public Token(String user) {
        this.user = user;
        this.token = generateToken();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
