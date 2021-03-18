package com.spotitube.controller;

import com.spotitube.controller.dto.TrackDTO;
import com.spotitube.mapper.DataMapper;
import com.spotitube.datasource.dao.TokenDAO;
import com.spotitube.datasource.dao.TrackDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class TrackController {

  @Inject
  private TokenDAO tokenDAO;

  @Inject
  private TrackDAO trackDAO;

  @Inject
  private DataMapper dataMapper;

  @GET
  @Path("/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllTracks(@QueryParam("forPlaylist") int playlistId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    return Response.status(200).entity(dataMapper.mapTracksDTO(playlistId, true)).build();
  }

  @GET
  @Path("/playlists/{playlistId}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTracksFromPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    return Response.status(200).entity(dataMapper.mapTracksDTO(playlistId, false)).build();
  }

  @POST
  @Path("/playlists/{playlistId}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addTrackToPlaylist(@PathParam("playlistId") int playlistId, TrackDTO trackDTO, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    trackDAO.addTrackToPlaylist(playlistId, trackDTO.id, trackDTO.offlineAvailable);
    return Response.status(201).entity(dataMapper.mapTracksDTO(playlistId, false)).build();
  }

  @DELETE
  @Path("/playlists/{playlistid}/tracks/{trackid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteTrackFromPlaylist(@PathParam("playlistid") int playlistId, @PathParam("trackid") int trackId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    trackDAO.deleteTrack(playlistId, trackId);
    return Response.status(200).entity(dataMapper.mapTracksDTO(playlistId, false)).build();
  }
}
