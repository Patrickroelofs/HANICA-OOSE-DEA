package com.spotitube.datasource.dao;

import com.spotitube.controller.dto.UserDTO;
import com.spotitube.datasource.IUserDAO;

import javax.annotation.Resource;
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
  public boolean verifyUser(UserDTO userDTO) {

    boolean result = false;

    try (Connection connection = dataSource.getConnection()) {
      String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, userDTO.user);
      preparedStatement.setString(2, userDTO.password);
      ResultSet resultSet = preparedStatement.executeQuery();

      if(resultSet.next()) {
        result = true;
      }

    } catch (SQLException e) {
      throw new InternalServerErrorException(e);
    }

    return result;
  }
}
