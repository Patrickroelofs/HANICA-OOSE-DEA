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
  public Response getAllTracks() {
    return null;
  }

  @GET
  @Path("/playlists/{playlistId}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTracksFromPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
    if(!tokenDAO.verify(token)) throw new ForbiddenException("Invalid Token");

    return Response.status(200).entity(tracksDTO(playlistId, token)).build();
  }

  @POST
  @Path("/{id}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addTrackToPlaylist() {
    return null;
  }

  @DELETE
  @Path("/{playlistid}/tracks/{trackid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteTrackFromPlaylist() {
    return null;
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
