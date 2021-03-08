package com.spotitube.controller;

import com.spotitube.controller.dto.UserDTO;
import com.spotitube.datasource.dao.UserDAO;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

@Path("user")
public class UserController {

  @Inject
  UserDAO userDAO;

  // TODO: Login user
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(UserDTO userDTO) {
    if(userDAO.isAuthenticated(userDTO)) {
      return Response.status(200).entity(userDTO).build();
    } else {
      return Response.status(401).build();
    }
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
