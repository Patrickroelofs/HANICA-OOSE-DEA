package com.spotitube.controller;

import com.spotitube.controller.dto.UserDTO;
import com.spotitube.datasource.dao.UserDAO;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

@Path("user")
public class UserController {

  @Resource(name = "jdbc/spotitube")
  DataSource dataSource;

  // TODO: Login user
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(UserDTO userDTO) {

    try {
      Connection connection = dataSource.getConnection();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }

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
