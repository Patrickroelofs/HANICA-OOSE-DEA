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
    public List<Track> getAllTracks(int forPlaylist, String token) {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM tracks LEFT JOIN playlists_tracks ON tracks.id = playlists_tracks.trackId WHERE playlists_tracks.playlistId = ?";
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
}
