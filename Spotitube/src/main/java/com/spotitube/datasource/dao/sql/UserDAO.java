package com.spotitube.datasource.dao.sql;

import com.spotitube.datasource.IUserDAO;
import com.spotitube.exceptions.SQLServerException;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Default
public class UserDAO implements IUserDAO {

  @Resource(name = "jdbc/spotitube")
  DataSource dataSource;

  @Override
  public boolean verifyUser(String username, String password) {

    boolean result = false;

    try (Connection connection = dataSource.getConnection()) {
      String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      ResultSet resultSet = preparedStatement.executeQuery();

      if(resultSet.next()) {
        result = true;
      }

      return result;

    } catch (SQLException e) {
      throw new SQLServerException(e);
    }
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
}
