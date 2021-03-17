package com.spotitube.datasource.dao;

import com.spotitube.controller.dto.TokenDTO;
import com.spotitube.datasource.ITokenDAO;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TokenDAO implements ITokenDAO {

  @Resource(name = "jdbc/spotitube")
  DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public TokenDTO insert(String username) {

    TokenDTO tokenDTO = new TokenDTO();
    tokenDTO.token = UUID.randomUUID().toString();
    tokenDTO.user = username;

    try (Connection connection = dataSource.getConnection()) {
      String sql = "UPDATE users SET token = ? WHERE username = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, tokenDTO.token);
      preparedStatement.setString(2, tokenDTO.user);
      preparedStatement.executeUpdate();

      return tokenDTO;

    } catch (SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }

  @Override
  public boolean verify(String token) {

    boolean result = false;

    try (Connection connection = dataSource.getConnection()) {
      String sql = "SELECT * FROM users WHERE token = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, token);
      ResultSet resultSet = preparedStatement.executeQuery();

      if(resultSet.next()) {
        result = true;
      }

    } catch(SQLException e) {
      throw new InternalServerErrorException(e);
    }

    return result;
  }

  @Override
  public String getUsername(String token) {
    try(Connection connection = dataSource.getConnection()) {
      String sql = "SELECT username FROM users WHERE token = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, token);
      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {
        return resultSet.getString(1);
      }

      return null;

    } catch(SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }
}
