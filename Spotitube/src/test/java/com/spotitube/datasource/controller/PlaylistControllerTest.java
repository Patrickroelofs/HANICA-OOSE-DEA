package com.spotitube.datasource.controller;

import com.spotitube.controller.PlaylistController;
import com.spotitube.controller.dto.PlaylistDTO;
import com.spotitube.controller.dto.PlaylistsDTO;
import com.spotitube.datasource.dao.PlaylistDAO;
import com.spotitube.datasource.dao.TokenDAO;
import com.spotitube.domain.Playlist;
import com.spotitube.exceptions.UnauthorizedUserException;
import com.spotitube.mapper.DataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistControllerTest {
    public String TOKEN = "111-111-111";

    PlaylistController playlistController;
    DataMapper dataMapper;
    PlaylistDAO playlistDAO;
    TokenDAO tokenDAO;

    @BeforeEach
    public void setup() {
        playlistController = new PlaylistController();

        playlistDAO = mock(PlaylistDAO.class);
        tokenDAO = mock(TokenDAO.class);
        dataMapper = mock(DataMapper.class);

        playlistController.setPlaylistDAO(playlistDAO);
        playlistController.setTokenDAO(tokenDAO);
        playlistController.setDataMapper(dataMapper);
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

        Response response = playlistController.getAllPlaylists(TOKEN);

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

        Response response = playlistController.editPlaylist(playlistDTO, TOKEN);

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

        Response response = playlistController.deletePlaylist(playlistDTO.id, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }

    @Test
    public void deletePlaylistThrowsUnauthorizedUserExceptionTest() {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.id = 1;
        playlistDTO.name = "Playlist1";

        when(tokenDAO.verify(TOKEN)).thenReturn(false);

        assertThrows(UnauthorizedUserException.class, () -> {
            playlistController.deletePlaylist(playlistDTO.id, TOKEN);
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

        Response response = playlistController.addPlaylist(playlistDTO, TOKEN);

        assertEquals(statusCodeExpected, response.getStatus());
    }
}
