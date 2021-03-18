package com.spotitube.datasource;

import com.spotitube.controller.dto.TokenDTO;
import com.spotitube.domain.Token;

public interface ITokenDAO {
  Token insert(String username);

  boolean verify(String token);

  String getUsername(String token);
}
