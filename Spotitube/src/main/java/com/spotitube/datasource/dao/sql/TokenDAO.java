package com.spotitube.datasource.dao.sql;

import com.spotitube.datasource.ITokenDAO;
import com.spotitube.domain.Token;
import com.spotitube.exceptions.SQLServerException;

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

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Token insert(String username) throws InternalServerErrorException {

    Token token = new Token(username);

    try (Connection connection = dataSource.getConnection()) {
      String sql = "UPDATE users SET token = ? WHERE username = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, token.getToken());
      preparedStatement.setString(2, token.getUser());
      preparedStatement.executeUpdate();

      return token;

    } catch (SQLException e) {
      throw new SQLServerException(e);
    }
  }

  @Override
  public boolean verify(String token) throws InternalServerErrorException {

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
      throw new SQLServerException(e);
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

      if(resultSet.next()) {
        return resultSet.getString(1);
      }

      return null;

    } catch(SQLException e) {
      throw new SQLServerException(e);
    }
  }
}
