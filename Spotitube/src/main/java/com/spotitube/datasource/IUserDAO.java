package com.spotitube.datasource;

public interface IUserDAO {
  boolean verifyUser(String username, String password);
}