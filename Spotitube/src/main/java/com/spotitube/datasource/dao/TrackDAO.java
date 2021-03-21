package com.spotitube.datasource.dao;

import com.spotitube.datasource.ITrackDAO;
import com.spotitube.domain.Track;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO implements ITrackDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public ArrayList<Track> getAllTracksNotInPlaylist(int forPlaylist) throws InternalServerErrorException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM tracks LEFT JOIN playlists_tracks pt on tracks.id = pt.trackId WHERE tracks.id NOT IN (SELECT trackId FROM playlists_tracks WHERE pt.playlistId = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, forPlaylist);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Track> tracks = new ArrayList<>();

            while(resultSet.next()) {
                Track track = new Track();
                track.setId(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("title"));
                track.setPerformer(resultSet.getString("performer"));
                track.setDuration(resultSet.getInt("duration"));
                track.setAlbum(resultSet.getString("album"));
                track.setPlaycount(resultSet.getInt("playcount"));
                track.setPublicationDate(resultSet.getString("publicationDate"));
                track.setDescription(resultSet.getString("description"));
                track.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));

                tracks.add(track);
            }

            return tracks;
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public ArrayList<Track> getAllTracks(int forPlaylist) throws InternalServerErrorException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM tracks LEFT JOIN playlists_tracks pt ON tracks.id = pt.trackId WHERE pt.playlistId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, forPlaylist);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Track> tracks = new ArrayList<>();

            while(resultSet.next()) {
                Track track = new Track();
                track.setId(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("title"));
                track.setPerformer(resultSet.getString("performer"));
                track.setDuration(resultSet.getInt("duration"));
                track.setAlbum(resultSet.getString("album"));
                track.setPlaycount(resultSet.getInt("playcount"));
                track.setPublicationDate(resultSet.getString("publicationDate"));
                track.setDescription(resultSet.getString("description"));
                track.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));

                tracks.add(track);
            }

            return tracks;
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void deleteTrack(int playlistId, int trackId) throws InternalServerErrorException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM playlists_tracks WHERE playlistId = ? AND trackId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable) throws InternalServerErrorException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO playlists_tracks (playlistId, trackId, offlineAvailable) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            preparedStatement.setBoolean(3, offlineAvailable);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
