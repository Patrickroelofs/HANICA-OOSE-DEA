package com.spotitube.controller;

import com.spotitube.controller.dto.UserDTO;
import com.spotitube.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class UserController {

  private UserService userService;

  @Inject
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  // TODO: Login user
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(UserDTO userDTO) {
    return userService.verifyUser(userDTO);
  }
}
