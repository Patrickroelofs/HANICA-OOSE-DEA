package com.spotitube.datasource.dao;

import com.spotitube.controller.dto.PlaylistDTO;
import com.spotitube.controller.dto.PlaylistsDTO;
import com.spotitube.datasource.IPlaylistDAO;
import com.spotitube.domain.Playlist;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO: Use interfaces instead of direct classes in controllers
public class PlaylistDAO implements IPlaylistDAO {

  @Resource(name = "jdbc/spotitube")
  DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void addPlaylist(String playlistName, String username, String token) {
    try (Connection connection = dataSource.getConnection()) {

      String sql = "INSERT INTO playlists (name, owner) VALUES (?, ?)";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, playlistName);
      preparedStatement.setString(2, username);
      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }

  @Override
  public List<Playlist> getAllPlaylists(String token) {
    try (Connection connection = dataSource.getConnection()) {
      String sql = "SELECT p.id, p.name, u.token, " +
              "(SELECT SUM(duration) FROM tracks INNER JOIN playlists_tracks pt on tracks.id = pt.trackId WHERE pt.playlistId = p.id) AS length FROM playlists p " +
              "INNER JOIN users u on p.owner = u.username";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet resultSet = preparedStatement.executeQuery();

      List<Playlist> playlists = new ArrayList<>();

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
      throw new InternalServerErrorException();
    }
  }

  @Override
  public void deletePlaylist(int id) {
    try(Connection connection = dataSource.getConnection()) {
      String sql = "DELETE FROM playlists WHERE id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }

  @Override
  public void editPlaylist(String playlistName, int playlistId, String token) {
    try(Connection connection = dataSource.getConnection()) {
      String sql = "UPDATE playlists set name = ? WHERE id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, playlistName);
      preparedStatement.setInt(2, playlistId);
      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }
}
