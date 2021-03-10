package com.spotitube.datasource;

import com.spotitube.controller.dto.UserDTO;

public interface IUserDAO {
  boolean verifyUser(UserDTO userDTO);
}