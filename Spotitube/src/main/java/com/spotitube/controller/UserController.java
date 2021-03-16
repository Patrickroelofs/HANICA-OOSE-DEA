package com.spotitube.controller;

import com.spotitube.controller.dto.UserDTO;
import com.spotitube.datasource.dao.TokenDAO;
import com.spotitube.datasource.dao.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class UserController {
  private UserDAO userDAO;
  private TokenDAO tokenDAO;


  // TODO: Login user
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(UserDTO userDTO) {
    if(userDAO.verifyUser(userDTO)) {
      tokenDAO.insert(userDTO.getUser());

      return Response.status(Response.Status.OK).entity(userDTO).build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }
}
