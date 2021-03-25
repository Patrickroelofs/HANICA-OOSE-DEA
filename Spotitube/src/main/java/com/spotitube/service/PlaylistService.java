package com.spotitube.service;

import com.spotitube.domain.Playlist;
import com.spotitube.service.dto.PlaylistDTO;
import com.spotitube.datasource.IPlaylistDAO;
import com.spotitube.datasource.ITokenDAO;
import com.spotitube.exceptions.UnauthorizedUserException;
import com.spotitube.service.dto.DTOMapper.DTOMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/")
public class PlaylistService {
  ITokenDAO tokenDAO;
  IPlaylistDAO playlistDAO;
  DTOMapper DTOMapper;

  @GET
  @Path("/playlists")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllPlaylists(@QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    ArrayList<Playlist> playlists = playlistDAO.getAllPlaylists(token);
    return Response.status(Response.Status.OK).entity(DTOMapper.mapPlaylistToPlaylistsDTO(playlists)).build();
  }

  @PUT
  @Path("/playlists/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response editPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    if(playlistDAO.editPlaylist(playlistDTO.name, playlistDTO.id)) {
      ArrayList<Playlist> playlists = playlistDAO.getAllPlaylists(token);
      return Response.status(Response.Status.OK).entity(DTOMapper.mapPlaylistToPlaylistsDTO(playlists)).build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  @DELETE
  @Path("/playlists/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    if(playlistDAO.deletePlaylist(id)) {
      ArrayList<Playlist> playlists = playlistDAO.getAllPlaylists(token);
      return Response.status(Response.Status.OK).entity(DTOMapper.mapPlaylistToPlaylistsDTO(playlists)).build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  @POST
  @Path("/playlists")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    if(playlistDAO.addPlaylist(playlistDTO.name, tokenDAO.getUsername(token))) {
      ArrayList<Playlist> playlists = playlistDAO.getAllPlaylists(token);
      return Response.status(Response.Status.OK).entity(DTOMapper.mapPlaylistToPlaylistsDTO(playlists)).build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  @Inject
  public void setTokenDAO(ITokenDAO tokenDAO) {
    this.tokenDAO = tokenDAO;
  }

  @Inject
  public void setPlaylistDAO(IPlaylistDAO playlistDAO) {
    this.playlistDAO = playlistDAO;
  }

  @Inject
  public void setDataMapper(DTOMapper DTOMapper) {
    this.DTOMapper = DTOMapper;
  }
}
