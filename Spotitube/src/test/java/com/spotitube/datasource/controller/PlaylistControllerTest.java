package com.spotitube.datasource.controller;

import com.spotitube.service.PlaylistService;
import com.spotitube.service.dto.PlaylistDTO;
import com.spotitube.service.dto.PlaylistsDTO;
import com.spotitube.datasource.dao.PlaylistDAO;
import com.spotitube.datasource.dao.TokenDAO;
import com.spotitube.domain.Playlist;
import com.spotitube.exceptions.UnauthorizedUserException;
import com.spotitube.mapper.DataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistControllerTest {
    public String TOKEN = "111-111-111";

    PlaylistService playlistService;
    DataMapper dataMapper;
    PlaylistDAO playlistDAO;
    TokenDAO tokenDAO;

    @BeforeEach
    public void setup() {
        playlistService = new PlaylistService();

        playlistDAO = mock(PlaylistDAO.class);
        tokenDAO = mock(TokenDAO.class);
        dataMapper = mock(DataMapper.class);

        playlistService.setPlaylistDAO(playlistDAO);
        playlistService.setTokenDAO(tokenDAO);
        playlistService.setDataMapper(dataMapper);
    }

    @Test
    public void getAllPlaylistsTest() {
        int statusCodeExpected = 200;

        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        PlaylistDTO playlistDTO = new PlaylistDTO();

        playlistDTO.id = 1;
        playlistDTO.name = "Playlist1";

        ArrayList<Playlist> playlists = new ArrayList<>();
        Playlist playlist = new Playlist();
        playlist.setId(1);
        playlist.setName("Playlist1");
        playlists.add(playlist);

        when(tokenDAO.verify(TOKEN)).thenReturn(true);
        when(dataMapper.mapPlaylistToPlaylistsDTO(TOKEN)).thenReturn(playlistsDTO);
        when(playlistDAO.getAllPlaylists(TOKEN)).thenReturn(playlists);

        Response response = playlistService.getAllPlaylists(TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void editPlaylistTest() {
        int statusCodeExpected = 200;

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.id = 1;
        playlistDTO.name = "Playlist1";

        when(tokenDAO.verify(TOKEN)).thenReturn(true);
        when(playlistDAO.editPlaylist(playlistDTO.name, playlistDTO.id)).thenReturn(true);

        Response response = playlistService.editPlaylist(playlistDTO, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void deletePlaylistTest() {
        int statusCodeExpected = 200;

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.id = 1;
        playlistDTO.name = "Playlist1";

        when(tokenDAO.verify(TOKEN)).thenReturn(true);
        when(playlistDAO.deletePlaylist(playlistDTO.id)).thenReturn(true);

        Response response = playlistService.deletePlaylist(playlistDTO.id, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void deletePlaylistThrowsUnauthorizedUserExceptionTest() {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.id = 1;
        playlistDTO.name = "Playlist1";

        when(tokenDAO.verify(TOKEN)).thenReturn(false);

        assertThrows(UnauthorizedUserException.class, () -> {
            playlistService.deletePlaylist(playlistDTO.id, TOKEN);
        });
    }

    @Test
    public void addPlaylistTest() {
        int statusCodeExpected = 200;
        String username = "patrick";

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.id = 1;
        playlistDTO.name = "Playlist1";

        when(tokenDAO.verify(TOKEN)).thenReturn(true);
        when(playlistDAO.addPlaylist(playlistDTO.name, tokenDAO.getUsername(TOKEN))).thenReturn(true);
        when(tokenDAO.getUsername(TOKEN)).thenReturn(username);

        Response response = playlistService.addPlaylist(playlistDTO, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }
}
