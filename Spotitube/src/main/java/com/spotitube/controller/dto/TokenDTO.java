package com.spotitube.controller.dto;

import java.util.UUID;

public class TokenDTO {
  private String user;
  private String token;

  public TokenDTO(String user) {
    this.user = user;
    this.token = generateToken();
  }

  public TokenDTO(String user, String token) {
    this.user = user;
    this.token = token;
  }

  public TokenDTO() {

  }

  public String generateToken() {
    return UUID.randomUUID().toString();
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
}
