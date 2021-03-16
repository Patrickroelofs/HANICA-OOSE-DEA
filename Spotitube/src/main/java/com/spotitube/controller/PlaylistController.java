package com.spotitube.controller;

import com.spotitube.controller.dto.PlaylistDTO;
import com.spotitube.controller.dto.PlaylistsDTO;
import com.spotitube.datasource.IPlaylistDAO;
import com.spotitube.datasource.dao.PlaylistDAO;
import com.spotitube.datasource.dao.TokenDAO;
import com.spotitube.domain.Playlist;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("playlists")
public class PlaylistController {

  @Inject
  TokenDAO tokenDAO;

  @Inject
  PlaylistDAO playlistDAO;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllPlaylists(@QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    return Response.status(Response.Status.OK).entity(playlistsDTO(token)).build();
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response editPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    playlistDAO.editPlaylist(playlistDTO.name, playlistDTO.id, token);
    return Response.status(200).entity(playlistsDTO(token)).build();
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    playlistDAO.deletePlaylist(id);
    return Response.status(Response.Status.OK).entity(playlistsDTO(token)).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    playlistDAO.addPlaylist(playlistDTO.name, tokenDAO.getUsername(token), token);
    return Response.status(Response.Status.OK).entity(playlistsDTO(token)).build();
  }

  public PlaylistsDTO playlistsDTO(String token) {
    List<Playlist> playlists = playlistDAO.getAllPlaylists(token);
    PlaylistsDTO playlistsDTO = new PlaylistsDTO();
    playlistsDTO.playlists = new ArrayList<>();

    int length = 0;
    for (Playlist playlist : playlists) {
      PlaylistDTO playlistDTO = new PlaylistDTO();
      playlistDTO.id = playlist.getId();
      playlistDTO.name = playlist.getName();
      playlistDTO.owner = playlist.isOwner();
      playlistsDTO.playlists.add(playlistDTO);
      length += playlist.getDuration();
    }

    playlistsDTO.length = length;
    return playlistsDTO;
  }
}
