package com.spotitube.controller.dto;

public class UserDTO {
  private String user;
  private String password;

  public UserDTO() {

  }

  public UserDTO(String username, String password) {
      setUser(username);
      setPassword(password);
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
