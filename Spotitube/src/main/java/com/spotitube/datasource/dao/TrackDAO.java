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
    public List<Track> getAllTracks(int forPlaylist, boolean toggler, String token) {
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            if(toggler) {
                sql = "SELECT * FROM tracks LEFT JOIN playlists_tracks pt on tracks.id = pt.trackId WHERE tracks.id NOT IN (SELECT trackId FROM playlists_tracks WHERE pt.playlistId = ?)";
            } else {
                sql = "SELECT * FROM tracks LEFT JOIN playlists_tracks pt ON tracks.id = pt.trackId WHERE pt.playlistId = ?";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, forPlaylist);
            ResultSet rs = preparedStatement.executeQuery();

            List<Track> tracks = new ArrayList<>();

            while(rs.next()) {
                Track track = new Track();
                track.setId(rs.getInt("id"));
                track.setTitle(rs.getString("title"));
                track.setPerformer(rs.getString("performer"));
                track.setDuration(rs.getInt("duration"));
                track.setAlbum(rs.getString("album"));
                track.setPlaycount(rs.getInt("playcount"));
                track.setPublicationDate(rs.getString("publicationDate"));
                track.setDescription(rs.getString("description"));
                track.setOfflineAvailable(rs.getBoolean("offlineAvailable"));

                tracks.add(track);
            }

            return tracks;
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void deleteTrack(int playlistId, int trackId) {
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
    public void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable) {
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
}
