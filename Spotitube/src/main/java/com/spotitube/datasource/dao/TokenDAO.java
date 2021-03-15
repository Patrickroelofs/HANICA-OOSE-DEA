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

public class TokenDAO implements ITokenDAO {

  @Resource(name = "jdbc/spotitube")
  DataSource dataSource;

  @Override
  public TokenDTO insert(String username) {

    TokenDTO tokenDTO = new TokenDTO(username);

    try (Connection connection = dataSource.getConnection()) {
      String sql = "UPDATE users SET token = ? WHERE username = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, tokenDTO.getToken());
      preparedStatement.setString(2, username);
      preparedStatement.executeUpdate();

      return tokenDTO;

    } catch (SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }
}
