package com.spotitube.datasource.controller;

import com.spotitube.service.TrackService;
import com.spotitube.service.dto.TrackDTO;
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

    TrackService trackService;
    DataMapper dataMapper;
    ITokenDAO tokenDAO;
    ITrackDAO trackDAO;
    TrackDTO trackDTO;

    @BeforeEach
    public void setup() {
        trackService = new TrackService();

        trackDAO = mock(ITrackDAO.class);
        tokenDAO = mock(ITokenDAO.class);
        dataMapper = mock(DataMapper.class);

        trackService.setTrackDAO(trackDAO);
        trackService.setTokenDAO(tokenDAO);
        trackService.setDataMapper(dataMapper);

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

        Response response = trackService.addTrackToPlaylist(PLAYLIST_ID, trackDTO, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void deleteTrackFromPlaylist() {
        int statusCodeExpected = 200;

        when(tokenDAO.verify(TOKEN)).thenReturn(true);

        Response response = trackService.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }
}
