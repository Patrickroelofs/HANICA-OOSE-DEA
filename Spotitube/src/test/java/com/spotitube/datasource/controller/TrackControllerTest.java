package com.spotitube.datasource.controller;

import com.spotitube.controller.TrackController;
import com.spotitube.controller.dto.TrackDTO;
import com.spotitube.datasource.ITokenDAO;
import com.spotitube.datasource.ITrackDAO;
import com.spotitube.mapper.DataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackControllerTest {
    public String TOKEN = "111-111-111";
    public int PLAYLIST_ID = 1;
    public int TRACK_ID = 1;
    public boolean OFFLINE_AVAILABLE = true;

    TrackController trackController;
    DataMapper dataMapper;
    ITokenDAO tokenDAO;
    ITrackDAO trackDAO;
    TrackDTO trackDTO;

    @BeforeEach
    public void setup() {
        trackController = new TrackController();

        trackDAO = mock(ITrackDAO.class);
        tokenDAO = mock(ITokenDAO.class);
        dataMapper = mock(DataMapper.class);

        trackController.setTrackDAO(trackDAO);
        trackController.setTokenDAO(tokenDAO);
        trackController.setDataMapper(dataMapper);

        trackDTO = new TrackDTO();
        trackDTO.id = TRACK_ID;
    }

    @Test
    public void getAllTracksTest() {
        int statusCodeExpected = 200;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);

        Response response = trackController.getAllTracks(PLAYLIST_ID, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void getTracksFromPlaylistTest() {
        int statusCodeExpected = 200;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);

        Response response = trackController.getTracksFromPlaylist(PLAYLIST_ID, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void addTrackToPlaylistTest() {
        int statusCodeExpected = 201;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);

        Response response = trackController.addTrackToPlaylist(PLAYLIST_ID, trackDTO, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void deleteTrackFromPlaylist() {
        int statusCodeExpected = 200;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);

        Response response = trackController.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }
}
