package com.spotitube.datasource.dao;

import com.spotitube.controller.dto.PlaylistDTO;
import com.spotitube.controller.dto.PlaylistsDTO;
import com.spotitube.datasource.IPlaylistDAO;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO {

  @Resource(name = "jdbc/spotitube")
  DataSource dataSource;

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
}
