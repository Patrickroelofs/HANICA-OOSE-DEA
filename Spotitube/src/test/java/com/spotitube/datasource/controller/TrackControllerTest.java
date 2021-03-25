package com.spotitube.datasource.controller;

import com.spotitube.service.TrackService;
import com.spotitube.service.dto.TrackDTO;
import com.spotitube.datasource.ITokenDAO;
import com.spotitube.datasource.ITrackDAO;
import com.spotitube.service.dto.DTOMapper.DTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackControllerTest {
    public final String TOKEN = "111-111-111";
    public final int PLAYLIST_ID = 1;
    public final int TRACK_ID = 1;
    public final boolean OFFLINE_AVAILABLE = true;

    TrackService trackService;
    DTOMapper DTOMapper;
    ITokenDAO tokenDAO;
    ITrackDAO trackDAO;
    TrackDTO trackDTO;

    @BeforeEach
    public void setup() {
        trackService = new TrackService();

        trackDAO = mock(ITrackDAO.class);
        tokenDAO = mock(ITokenDAO.class);
        DTOMapper = mock(DTOMapper.class);

        trackService.setTrackDAO(trackDAO);
        trackService.setTokenDAO(tokenDAO);
        trackService.setDataMapper(DTOMapper);

        trackDTO = new TrackDTO();
        trackDTO.id = TRACK_ID;
    }

    @Test
    public void getAllTracksTest() {
        int statusCodeExpected = 200;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);

        Response response = trackService.getAllTracks(PLAYLIST_ID, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void getTracksFromPlaylistTest() {
        int statusCodeExpected = 200;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);

        Response response = trackService.getTracksFromPlaylist(PLAYLIST_ID, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void addTrackToPlaylistTest() {
        int statusCodeExpected = 201;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);
        when(trackDAO.addTrackToPlaylist(eq(PLAYLIST_ID), eq(trackDTO.id), anyBoolean())).thenReturn(true);

        Response response = trackService.addTrackToPlaylist(PLAYLIST_ID, trackDTO, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void addTrackToPlaylistTrackCannotBeAddedTest() {
        int statusCodeExpected = 403;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);
        when(trackDAO.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, OFFLINE_AVAILABLE)).thenReturn(false);

        Response response = trackService.addTrackToPlaylist(PLAYLIST_ID, trackDTO, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void deleteTrackFromPlaylistTest() {
        int statusCodeExpected = 200;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);
        when(trackDAO.deleteTrack(PLAYLIST_ID, TRACK_ID)).thenReturn(true);

        Response response = trackService.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void deleteTrackFromPlaylistCannotBeDeletedTest() {
        int statusCodeExpected = 403;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);
        when(trackDAO.deleteTrack(PLAYLIST_ID, TRACK_ID)).thenReturn(false);

        Response response = trackService.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }
}
