package com.spotitube.controller;

import com.spotitube.controller.dto.TokenDTO;
import com.spotitube.controller.dto.UserDTO;
import com.spotitube.datasource.dao.TokenDAO;
import com.spotitube.datasource.dao.UserDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class UserController {

  @Inject
  UserDAO userDAO;

  @Inject
  TokenDAO tokenDAO;

  // TODO: Login user
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(UserDTO userDTO) {
    if(userDAO.verifyUser(userDTO)) {
      TokenDTO token = tokenDAO.insert(userDTO.getUser());

      return Response.status(200).entity(token).build();
    } else {
      return Response.status(401).build();
    }
  }
}
