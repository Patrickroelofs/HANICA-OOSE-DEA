package com.spotitube.service;

import com.spotitube.service.dto.TrackDTO;
import com.spotitube.datasource.ITokenDAO;
import com.spotitube.datasource.ITrackDAO;
import com.spotitube.exceptions.UnauthorizedUserException;
import com.spotitube.mapper.DataMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class TrackService {

  private ITokenDAO tokenDAO;
  private ITrackDAO trackDAO;
  private DataMapper dataMapper;

  @GET
  @Path("/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllTracks(@QueryParam("forPlaylist") int playlistId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    return Response.status(200).entity(dataMapper.mapTracksToTracksDTO(playlistId, true)).build();
  }

  @GET
  @Path("/playlists/{playlistId}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTracksFromPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    return Response.status(200).entity(dataMapper.mapTracksToTracksDTO(playlistId, false)).build();
  }

  @POST
  @Path("/playlists/{playlistId}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addTrackToPlaylist(@PathParam("playlistId") int playlistId, TrackDTO trackDTO, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    trackDAO.addTrackToPlaylist(playlistId, trackDTO.id, trackDTO.offlineAvailable);
    return Response.status(201).entity(dataMapper.mapTracksToTracksDTO(playlistId, false)).build();
  }

  @DELETE
  @Path("/playlists/{playlistid}/tracks/{trackid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteTrackFromPlaylist(@PathParam("playlistid") int playlistId, @PathParam("trackid") int trackId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    trackDAO.deleteTrack(playlistId, trackId);
    return Response.status(200).entity(dataMapper.mapTracksToTracksDTO(playlistId, false)).build();
  }

  @Inject
  public void setTokenDAO(ITokenDAO tokenDAO) {
    this.tokenDAO = tokenDAO;
  }

  @Inject
  public void setTrackDAO(ITrackDAO trackDAO) {
    this.trackDAO = trackDAO;
  }

  @Inject
  public void setDataMapper(DataMapper dataMapper) {
    this.dataMapper = dataMapper;
  }
}
