package com.spotitube.domain;

import java.util.UUID;

public class Token {
    private final String user;
    private final String token;

    public Token(String user) {
        this.user = user;
        this.token = generateToken();
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
