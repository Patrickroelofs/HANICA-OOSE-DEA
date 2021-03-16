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
  private UserDAO userDAO;

  @Inject
  private TokenDAO tokenDAO;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(UserDTO userDTO) {
    if(userDAO.verifyUser(userDTO)) {
      TokenDTO tokenDTO = tokenDAO.insert(userDTO.getUser());

      return Response.status(Response.Status.OK).entity(tokenDTO).build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }
}
