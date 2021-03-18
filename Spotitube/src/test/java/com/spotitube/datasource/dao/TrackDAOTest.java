package com.spotitube.datasource.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class TrackDAOTest {
    public int PLAYLIST_ID = 1;
    public int TRACK_ID = 1;

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

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getAllTracksThrowsInternalServerErrorTest() {
        try {
            when(dataSource.getConnection()).thenThrow(new InternalServerErrorException());

            trackDAO.getAllTracks(PLAYLIST_ID, false);

            assertThrows(InternalServerErrorException.class, () -> {
                trackDAO.getAllTracks(PLAYLIST_ID, false);
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
            when(dataSource.getConnection()).thenThrow(new InternalServerErrorException());

            trackDAO.deleteTrack(PLAYLIST_ID, TRACK_ID);

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
            when(dataSource.getConnection()).thenThrow(new SQLException());

            trackDAO.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, true);

            assertThrows(SQLException.class, () -> {
                trackDAO.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, true);
            });

        } catch (Exception e) {
            fail(e);
        }
    }
}
