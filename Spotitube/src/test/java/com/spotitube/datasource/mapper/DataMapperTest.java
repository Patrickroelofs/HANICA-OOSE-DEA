package com.spotitube.datasource.mapper;

import com.spotitube.controller.dto.PlaylistDTO;
import com.spotitube.controller.dto.PlaylistsDTO;
import com.spotitube.datasource.dao.PlaylistDAO;
import com.spotitube.datasource.dao.UserDAO;
import com.spotitube.domain.Playlist;
import com.spotitube.mapper.DataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class DataMapperTest {
    private String TOKEN = "111-111-111";
    private String PLAYLIST_NAME = "Playlist1";
    private int PLAYLIST_ID = 1;
    private String USERNAME = "patrick";

    private UserDAO userDAO;
    private PlaylistDAO playlistDAO;
    private PlaylistDAO mockPlaylistDAO;
    private DataMapper dataMapper;
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    @BeforeEach
    public void setup() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        userDAO = new UserDAO();
        playlistDAO = new PlaylistDAO();
        dataMapper = new DataMapper();
        mockPlaylistDAO = Mockito.spy(playlistDAO);

        userDAO.setDataSource(dataSource);
        playlistDAO.setDataSource(dataSource);
        dataMapper.setPlaylistDAO(playlistDAO);
    }

    @Test
    public void mapPlaylistToPlaylistsDTOTest() {
        try {

            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.id = 1;
            playlistDTO.name = "HipHop playlist";
            playlistDTO.owner = true;

            PlaylistsDTO playlistsDTO = new PlaylistsDTO();
            playlistsDTO.playlists = new ArrayList<>();
            playlistsDTO.playlists.add(playlistDTO);
            playlistsDTO.length = 100;

            ArrayList<Playlist> playlistList = new ArrayList<>();

            Playlist playlist = new Playlist();
            playlist.setId(playlistDTO.id);
            playlist.setName(playlistDTO.name);
            playlist.setOwner(playlistDTO.owner);
            playlist.setDuration(playlistsDTO.length);
            playlist.setTracks(null);

            playlistList.add(playlist);

            String sql = "SELECT p.id, p.name, u.token, " +
                    "(SELECT SUM(duration) FROM tracks INNER JOIN playlists_tracks pt on tracks.id = pt.trackId WHERE pt.playlistId = p.id) AS length FROM playlists p " +
                    "INNER JOIN users u on p.owner = u.username";


            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("p.id")).thenReturn(PLAYLIST_ID);
            when(resultSet.getString("p.name")).thenReturn(PLAYLIST_NAME);
            when(resultSet.getString("u.token")).thenReturn(TOKEN);
            when(resultSet.getInt("length")).thenReturn(100);

            doReturn(playlistList).when(mockPlaylistDAO).getAllPlaylists(TOKEN);

            PlaylistsDTO actual = dataMapper.mapPlaylistToPlaylistsDTO(TOKEN);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(sql);

            assertEquals(playlistsDTO.length, actual.length);

        } catch (Exception e) {
            fail(e);
        }
    }
}
