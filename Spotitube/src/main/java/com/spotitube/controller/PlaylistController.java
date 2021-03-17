package com.spotitube.controller;

import com.spotitube.controller.dto.PlaylistDTO;
import com.spotitube.datamapper.DataMapper;
import com.spotitube.datasource.dao.PlaylistDAO;
import com.spotitube.datasource.dao.TokenDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class PlaylistController {

  @Inject
  TokenDAO tokenDAO;

  @Inject
  PlaylistDAO playlistDAO;

  @Inject
  DataMapper dataMapper;

  @GET
  @Path("/playlists")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllPlaylists(@QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    return Response.status(Response.Status.OK).entity(dataMapper.mapPlaylistDTO(token)).build();
  }

  @PUT
  @Path("/playlists/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response editPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    playlistDAO.editPlaylist(playlistDTO.name, playlistDTO.id, token);
    return Response.status(200).entity(dataMapper.mapPlaylistDTO(token)).build();
  }

  @DELETE
  @Path("/playlists/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    playlistDAO.deletePlaylist(id);
    return Response.status(Response.Status.OK).entity(dataMapper.mapPlaylistDTO(token)).build();
  }

  @POST
  @Path("/playlists")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    playlistDAO.addPlaylist(playlistDTO.name, tokenDAO.getUsername(token), token);
    return Response.status(Response.Status.OK).entity(dataMapper.mapPlaylistDTO(token)).build();
  }
}
