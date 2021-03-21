package com.spotitube.datasource.dao;

import com.spotitube.domain.Playlist;
import com.spotitube.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackDAOTest {
    public int PLAYLIST_ID = 1;
    public int TRACK_ID = 1;
    public String TRACK_TITLE = "Track Title";
    public String TRACK_PERFORMER = "Performer";
    public String PLAYLIST_TITLE = "PlaylistTitle";
    public String PLAYLIST_PERFORMER = "PlaylistTitle";
    public int PLAYLIST_DURATION = 100;
    public String PLAYLIST_ALBUM = "PlaylistTitle";
    public int PLAYLIST_PLAYCOUNT = 5;
    public String PLAYLIST_PUBLICATIONDATE = "PlaylistTitle";
    public String PLAYLIST_DESCRIPTION = "PlaylistTitle";
    public boolean PLAYLIST_OFFLINEAVAILABLE = false;

    private TrackDAO trackDAO;
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

        trackDAO = new TrackDAO();
        trackDAO.setDataSource(dataSource);
    }

    @Test
    public void getAllTracksTest() {
        try {
            String sql = "SELECT * FROM tracks LEFT JOIN playlists_tracks pt ON tracks.id = pt.trackId WHERE pt.playlistId = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("id")).thenReturn(PLAYLIST_ID);
            when(resultSet.getString("title")).thenReturn(PLAYLIST_TITLE);
            when(resultSet.getString("performer")).thenReturn(PLAYLIST_PERFORMER);
            when(resultSet.getInt("duration")).thenReturn(PLAYLIST_DURATION);
            when(resultSet.getString("album")).thenReturn(PLAYLIST_ALBUM);
            when(resultSet.getInt("playcount")).thenReturn(PLAYLIST_PLAYCOUNT);
            when(resultSet.getString("publicationDate")).thenReturn(PLAYLIST_PUBLICATIONDATE);
            when(resultSet.getString("description")).thenReturn(PLAYLIST_DESCRIPTION);
            when(resultSet.getBoolean("offlineAvailable")).thenReturn(PLAYLIST_OFFLINEAVAILABLE);

            ArrayList<Track> tracksResult = trackDAO.getAllTracks(PLAYLIST_ID);

            verify(dataSource).getConnection();
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(connection).prepareStatement(sql);

            assertEquals(PLAYLIST_ID, tracksResult.get(0).getId());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getAllTracksTogglerSetToTrueTest() {
        try {
            String sql = "SELECT * FROM tracks LEFT JOIN playlists_tracks pt on tracks.id = pt.trackId WHERE tracks.id NOT IN (SELECT trackId FROM playlists_tracks WHERE pt.playlistId = ?)";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("id")).thenReturn(PLAYLIST_ID);
            when(resultSet.getString("title")).thenReturn(PLAYLIST_TITLE);
            when(resultSet.getString("performer")).thenReturn(PLAYLIST_PERFORMER);
            when(resultSet.getInt("duration")).thenReturn(PLAYLIST_DURATION);
            when(resultSet.getString("album")).thenReturn(PLAYLIST_ALBUM);
            when(resultSet.getInt("playcount")).thenReturn(PLAYLIST_PLAYCOUNT);
            when(resultSet.getString("publicationDate")).thenReturn(PLAYLIST_PUBLICATIONDATE);
            when(resultSet.getString("description")).thenReturn(PLAYLIST_DESCRIPTION);
            when(resultSet.getBoolean("offlineAvailable")).thenReturn(PLAYLIST_OFFLINEAVAILABLE);

            ArrayList<Track> tracksResult = trackDAO.getAllTracksNotInPlaylist(PLAYLIST_ID);

            verify(dataSource).getConnection();
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(connection).prepareStatement(sql);

            assertEquals(PLAYLIST_ID, tracksResult.get(0).getId());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getAllTracksThrowsInternalServerErrorTest() {
        try {
            String sql = "SELECT * FROM tracks LEFT JOIN playlists_tracks pt ON tracks.id = pt.trackId WHERE pt.playlistId = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> {
                trackDAO.getAllTracks(PLAYLIST_ID);
            });

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getAllTracksNotInPlaylistThrowsInternalServerErrorTest() {
        try {
            String sql = "SELECT * FROM tracks LEFT JOIN playlists_tracks pt on tracks.id = pt.trackId WHERE tracks.id NOT IN (SELECT trackId FROM playlists_tracks WHERE pt.playlistId = ?)";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> {
                trackDAO.getAllTracksNotInPlaylist(PLAYLIST_ID);
            });

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void deleteTrackTest() {
        try {
            String sql = "DELETE FROM playlists_tracks WHERE playlistId = ? AND trackId = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

            trackDAO.deleteTrack(PLAYLIST_ID, TRACK_ID);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(preparedStatement).setInt(2, TRACK_ID);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void deleteTrackThrowsInternalServerErrorTest() {
        try {
            String sql = "DELETE FROM playlists_tracks WHERE playlistId = ? AND trackId = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> {
                trackDAO.deleteTrack(PLAYLIST_ID, TRACK_ID);
            });

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void addTrackToPlaylistTest() {
        try {
            String sql = "INSERT INTO playlists_tracks (playlistId, trackId, offlineAvailable) VALUES (?, ?, ?)";
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

            trackDAO.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, true);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(preparedStatement).setInt(2, TRACK_ID);
            verify(preparedStatement).setBoolean(3, true);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void addTrackToPlaylistThrowsInternalServerErrorTest() {
        try {
            String sql = "INSERT INTO playlists_tracks (playlistId, trackId, offlineAvailable) VALUES (?, ?, ?)";
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

            assertThrows(InternalServerErrorException.class, () -> {
                trackDAO.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, PLAYLIST_OFFLINEAVAILABLE);
            });
        } catch (Exception e) {
            fail(e);
        }
    }
}
