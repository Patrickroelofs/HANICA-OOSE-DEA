package com.spotitube.datasource.dao;

import com.spotitube.domain.Playlist;
import com.spotitube.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistDAOTest {
    private PlaylistDAO playlistDAO;
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private ArrayList<Track> expectedTracks = new ArrayList<>();
    private Track expectedTrack1 = new Track(1, "Track 1", "Performer 1");
    private Track expectedTrack2 = new Track(2, "Track 2", "Performer 2");

    private String TOKEN = "111-111-111";
    private String PLAYLIST_NAME = "Playlist1";
    private int PLAYLIST_ID = 1;

    private Playlist expectedPlaylist = new Playlist();
    private ArrayList<Playlist> expectedPlaylists = new ArrayList<>();

    @BeforeEach
    public void setup() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);


        playlistDAO = new PlaylistDAO();
        playlistDAO.setDataSource(dataSource);

        expectedPlaylist.setName(PLAYLIST_NAME);
        expectedPlaylist.setId(PLAYLIST_ID);

        expectedPlaylists.add(expectedPlaylist);

        expectedTrack1.setDuration(100);
        expectedTrack2.setDuration(100);
        expectedTracks.add(expectedTrack1);
        expectedTracks.add(expectedTrack2);
    }

    @Test
    public void getAllPlaylistsTest() {
        try {
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

            ArrayList<Playlist> playlistsResult = playlistDAO.getAllPlaylists(TOKEN);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(sql);

            assertEquals(PLAYLIST_ID, playlistsResult.get(0).getId());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void addPlaylistTest() {
        try {
            String sql = "INSERT INTO playlists (name, owner) VALUES (?, ?)";
            String expectedPlaylistName = "playlist1";
            String expectedUsername = "patrick";

            Playlist expectedPlaylist = new Playlist(expectedPlaylistName);
            expectedPlaylist.setTracks(expectedTracks);

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            boolean createPlaylist = playlistDAO.addPlaylist(expectedPlaylistName, expectedUsername);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setString(1, expectedPlaylistName);
            verify(preparedStatement).setString(2, expectedUsername);

            assertTrue(createPlaylist);

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void deletePlaylistTest() {
        try {
            String sql = "DELETE FROM playlists WHERE id = ?";
            int expected = 1;

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            boolean deletePlaylist = playlistDAO.deletePlaylist(expected);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setInt(1, expected);

            assertTrue(deletePlaylist);

        } catch (Exception e) {
            fail(e);
        }

    }

    @Test
    public void editPlaylistTest() {
        try {
            String sql = "UPDATE playlists set name = ? WHERE id = ?";

            int expectedId = 1;
            boolean expectedOwner = true;
            String expectedName = "playlist1";

            Playlist expectedPlaylist = new Playlist(expectedId, expectedName, expectedOwner);
            expectedPlaylist.setTracks(expectedTracks);

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);

            when(resultSet.getInt(1)).thenReturn(expectedId);

            boolean editPlaylist = playlistDAO.editPlaylist(expectedName, expectedId);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setString(1, expectedName);
            verify(preparedStatement).setInt(2, expectedId);

            assertTrue(editPlaylist);

        } catch (Exception e) {
            fail(e);
        }
    }

}
