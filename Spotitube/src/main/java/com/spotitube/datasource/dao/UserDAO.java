package com.spotitube.datasource.dao;

import com.spotitube.controller.dto.TokenDTO;
import com.spotitube.controller.dto.UserDTO;
import com.spotitube.datasource.IUserDAO;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDAO implements IUserDAO {

  @Resource(name = "jdbc/spotitube")
  DataSource dataSource;

  @Override
  public boolean isAuthenticated(UserDTO userDTO) {

    boolean result = false;

    try (Connection connection = dataSource.getConnection()) {
      String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, userDTO.getUsername());
      preparedStatement.setString(2, userDTO.getPassword());
      ResultSet resultSet = preparedStatement.executeQuery();

      if(resultSet.next()) {
        String token = generateToken();
        TokenDTO tokenDTO = new TokenDTO(userDTO.getUsername(), token);
        updateToken(tokenDTO);

        result = true;
      }

    } catch (SQLException e) {
      throw new InternalServerErrorException(e);
    }

    return result;
  }

  @Override
  public void updateToken(TokenDTO tokenDTO) {
    try(Connection connection = dataSource.getConnection()) {
      String sql = "UPDATE users SET token = ? WHERE username = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, tokenDTO.getToken());
      preparedStatement.setString(2, tokenDTO.getUsername());
      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }

  public String generateToken() {
    return UUID.randomUUID().toString();
  }
}
