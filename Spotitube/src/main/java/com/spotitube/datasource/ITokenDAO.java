package com.spotitube.datasource;

import com.spotitube.controller.dto.TokenDTO;

public interface ITokenDAO {
  TokenDTO insert(String username);
}
