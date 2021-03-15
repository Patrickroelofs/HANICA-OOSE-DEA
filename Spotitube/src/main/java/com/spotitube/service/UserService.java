package com.spotitube.service;

import com.spotitube.controller.dto.TokenDTO;
import com.spotitube.controller.dto.UserDTO;
import com.spotitube.datasource.dao.TokenDAO;
import com.spotitube.datasource.dao.UserDAO;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class UserService {
  private UserDAO userDAO;
  private TokenDAO tokenDAO;

  @Inject
  public void setUserDAO(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Inject
  public void setTokenDAO(TokenDAO tokenDAO) {
    this.tokenDAO = tokenDAO;
  }

  public Response verifyUser(UserDTO userDTO) {
    if(userDAO.verifyUser(userDTO)) {
      // do token thing
      tokenDAO.insert(userDTO.getUser());

      // return token dto
      return Response.status(Response.Status.OK).entity(userDTO).build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }
}
