package com.spotitube.controller;

import com.spotitube.controller.dto.TrackDTO;
import com.spotitube.controller.dto.TracksDTO;
import com.spotitube.datasource.dao.TokenDAO;
import com.spotitube.datasource.dao.TrackDAO;
import com.spotitube.domain.Track;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class TrackController {

  @Inject
  private TokenDAO tokenDAO;

  @Inject
  private TrackDAO trackDAO;

  @GET
  @Path("/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllTracks(@QueryParam("forPlaylist") int playlistId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    return Response.status(200).entity(tracksDTO(playlistId, token)).build();
  }

  @GET
  @Path("/playlists/{playlistId}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTracksFromPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    return Response.status(200).entity(tracksDTO(playlistId, token)).build();
  }

  @POST
  @Path("/playlists/{id}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addTrackToPlaylist(@PathParam("id") int playlistId, TrackDTO trackDTO, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    trackDAO.addTrackToPlaylist(playlistId, trackDTO.id, trackDTO.offlineAvailable);
    return Response.status(201).entity(tracksDTO(playlistId, token)).build();
  }

  @DELETE
  @Path("/playlists/{playlistid}/tracks/{trackid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteTrackFromPlaylist(@PathParam("playlistid") int playlistId, @PathParam("trackid") int trackId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    trackDAO.deleteTrack(playlistId, trackId);
    return Response.status(200).entity(tracksDTO(playlistId, token)).build();
  }

  public TracksDTO tracksDTO(int forPlaylist, String token) {
    List<Track> tracks = trackDAO.getAllTracks(forPlaylist, token);
    TracksDTO tracksDTO = new TracksDTO();
    tracksDTO.tracks = new ArrayList<>();

    for (Track track : tracks) {
      TrackDTO trackDTO = new TrackDTO();
      trackDTO.id = track.getId();
      trackDTO.title = track.getTitle();
      trackDTO.performer = track.getPerformer();
      trackDTO.duration = track.getDuration();
      trackDTO.album = track.getAlbum();
      trackDTO.playcount = track.getPlaycount();
      trackDTO.publicationDate = track.getPublicationDate();
      trackDTO.description = track.getDescription();
      trackDTO.offlineAvailable = track.getOfflineAvailable();

      tracksDTO.tracks.add(trackDTO);
    }
    return tracksDTO;
  }
}
