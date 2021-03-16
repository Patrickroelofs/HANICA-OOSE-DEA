package com.spotitube.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class TrackController {

  @GET
  @Path("/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllTracks() {
    return null;
  }

  @GET
  @Path("/{playlist}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTracksFromPlaylist() {
    return null;
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
}
