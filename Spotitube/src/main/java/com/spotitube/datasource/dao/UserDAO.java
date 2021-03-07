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

public class UserDAO implements IUserDAO {

  @Resource(name = "jdbc/Spotitube")
  private DataSource dataSource;

  @Override
  public boolean isAuthenticated(UserDTO userDTO) {
    try {

      Connection connection = dataSource.getConnection();
      String sql = "SELECT * FROM users WHERE username = 'patrick' AND password = '112112'";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet resultSet = preparedStatement.executeQuery();

      return resultSet.first();

    } catch (SQLException e) {
      throw new InternalServerErrorException(e);
    }
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
}
