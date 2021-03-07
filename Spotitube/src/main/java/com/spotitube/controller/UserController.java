package com.spotitube.controller;

import com.spotitube.controller.dto.UserDTO;
import com.spotitube.datasource.dao.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserController {

  // TODO: Login user
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(UserDTO userDTO) {

    UserDAO userDAO = new UserDAO();
    userDAO.isAuthenticated(userDTO);

    return Response.status(200).entity(userDTO).build();
  }

  // TODO: Create user
  @POST
  @Path("create")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response register(UserDTO userDTO) {
    return Response.status(200).entity(userDTO).build();
  }

  // TODO: Delete user
  @POST
  @Path("delete")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(UserDTO userDTO) {
    return Response.status(200).entity(userDTO).build();
  }
}
