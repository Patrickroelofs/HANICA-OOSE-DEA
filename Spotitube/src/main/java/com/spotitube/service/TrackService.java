package com.spotitube.service;

import com.spotitube.domain.Track;
import com.spotitube.service.dto.TrackDTO;
import com.spotitube.datasource.ITokenDAO;
import com.spotitube.datasource.ITrackDAO;
import com.spotitube.exceptions.UnauthorizedUserException;
import com.spotitube.service.dto.DTOMapper.DTOMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/")
public class TrackService {

  private ITokenDAO tokenDAO;
  private ITrackDAO trackDAO;
  private DTOMapper DTOMapper;

  @GET
  @Path("/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllTracks(@QueryParam("forPlaylist") int playlistId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    ArrayList<Track> tracks = trackDAO.getAllTracksNotInPlaylist(playlistId);
    return Response.status(Response.Status.OK).entity(DTOMapper.mapTracksToTracksDTO(tracks)).build();
  }

  @GET
  @Path("/playlists/{playlistId}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTracksFromPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    ArrayList<Track> tracks = trackDAO.getAllTracks(playlistId);
    return Response.status(Response.Status.OK).entity(DTOMapper.mapTracksToTracksDTO(tracks)).build();
  }

  @POST
  @Path("/playlists/{playlistId}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addTrackToPlaylist(@PathParam("playlistId") int playlistId, TrackDTO trackDTO, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    if(trackDAO.addTrackToPlaylist(playlistId, trackDTO.id, trackDTO.offlineAvailable)) {
      ArrayList<Track> tracks = trackDAO.getAllTracks(playlistId);
      return Response.status(Response.Status.CREATED).entity(DTOMapper.mapTracksToTracksDTO(tracks)).build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  @DELETE
  @Path("/playlists/{playlistid}/tracks/{trackid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteTrackFromPlaylist(@PathParam("playlistid") int playlistId, @PathParam("trackid") int trackId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new UnauthorizedUserException("Invalid Token");

    if(trackDAO.deleteTrack(playlistId, trackId)) {
      ArrayList<Track> tracks = trackDAO.getAllTracks(playlistId);
      return Response.status(Response.Status.OK).entity(DTOMapper.mapTracksToTracksDTO(tracks)).build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
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
  public void setDataMapper(DTOMapper DTOMapper) {
    this.DTOMapper = DTOMapper;
  }
}
