package com.spotitube.controller;

import com.spotitube.controller.dto.PlaylistsDTO;
import com.spotitube.datasource.dao.PlaylistDAO;
import com.spotitube.datasource.dao.TokenDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("playlists")
public class PlaylistController {

  @Inject
  private TokenDAO tokenDAO;

  @Inject
  private PlaylistDAO playlistDAO;

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response editPlaylist() {
    return null;
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePlaylist() {
    return null;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPlaylist() {
    return null;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllPlaylists() {
    return null;
  }
}
