package com.spotitube.service;

import com.spotitube.domain.User;
import com.spotitube.service.dto.UserDTO;
import com.spotitube.datasource.ITokenDAO;
import com.spotitube.datasource.IUserDAO;
import com.spotitube.domain.Token;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class UserService {

  private IUserDAO userDAO;
  private ITokenDAO tokenDAO;

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(UserDTO userDTO) {
    if(userDAO.verifyUser(userDTO.user, userDTO.password)) {
      Token token = tokenDAO.insert(userDTO.user);
      User user = new User(userDTO.user, userDTO.password);

      return Response.status(Response.Status.OK).entity(token).build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  @Inject
  public void setUserDAO(IUserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Inject
  public void setTokenDAO(ITokenDAO tokenDAO) {
    this.tokenDAO = tokenDAO;
  }
}
