package com.spotitube.datasource.dao;

import com.spotitube.datasource.IPlaylistDAO;
import com.spotitube.domain.Playlist;
import com.spotitube.exceptions.SQLServerException;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO implements IPlaylistDAO {

  @Resource(name = "jdbc/spotitube")
  DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public boolean addPlaylist(String playlistName, String username) {
    try (Connection connection = dataSource.getConnection()) {

      String sql = "INSERT INTO playlists (name, owner) VALUES (?, ?)";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, playlistName);
      preparedStatement.setString(2, username);
      preparedStatement.executeUpdate();

      return true;

    } catch (SQLException e) {
      throw new SQLServerException(e);
    }

  }

  @Override
  public ArrayList<Playlist> getAllPlaylists(String token) {
    try (Connection connection = dataSource.getConnection()) {
      String sql = "SELECT p.id, p.name, u.token, " +
              "(SELECT SUM(duration) FROM tracks INNER JOIN playlists_tracks pt on tracks.id = pt.trackId WHERE pt.playlistId = p.id) AS length FROM playlists p " +
              "INNER JOIN users u on p.owner = u.username";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet resultSet = preparedStatement.executeQuery();

      ArrayList<Playlist> playlists = new ArrayList<>();

      while (resultSet.next()) {
        Playlist playlist = new Playlist();
        playlist.setId(resultSet.getInt("p.id"));
        playlist.setName(resultSet.getString("p.name"));
        playlist.setOwner(resultSet.getString("u.token").equals(token));

        playlist.setDuration(resultSet.getInt("length"));
        playlists.add(playlist);
      }
      return playlists;
    } catch (SQLException e) {
      throw new SQLServerException(e);
    }
  }

  @Override
  public boolean deletePlaylist(int id) {
    try(Connection connection = dataSource.getConnection()) {
      String sql = "DELETE FROM playlists WHERE id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();

      return true;

    } catch (SQLException e) {
      throw new SQLServerException(e);
    }
  }

  public boolean editPlaylist(String playlistName, int playlistId) {
    try(Connection connection = dataSource.getConnection()) {
      String sql = "UPDATE playlists set name = ? WHERE id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, playlistName);
      preparedStatement.setInt(2, playlistId);
      preparedStatement.executeUpdate();

      return true;

    } catch (SQLException e) {
      throw new SQLServerException(e);
    }

  }
}
