package com.spotitube.datasource;

import com.spotitube.controller.dto.UserDTO;

public interface IUserDAO {
  boolean isAuthenticated(UserDTO userDTO);
}
