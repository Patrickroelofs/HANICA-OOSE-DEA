package com.spotitube.controller;

import com.spotitube.controller.dto.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserController {

  // Testing path to ensure atleast something is working!
  @GET
  @Path("test")
  public String test() {
    return "UserController test path is working.";
  }

  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(UserDTO userDTO) {
    return Response.status(200).entity(userDTO).build();
  }
}
