package com.spotitube.datasource.dao;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    private final String USERNAME = "patrick";
    private final String PASSWORD = "112112";

    DataSource dataSource = mock(DataSource.class);
    Connection connection = mock(Connection.class);
    PreparedStatement preparedStatement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    UserDAO userDAO = new UserDAO();

    @Test
    public void loginWithValidUsernameAndPassword() {
        try {
            String expectedSQL = "SELECT * FROM users WHERE username = ? AND password = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);

            userDAO.setDataSource(dataSource);

            boolean verified = userDAO.verifyUser(USERNAME, PASSWORD);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, USERNAME);
            verify(preparedStatement).setString(2, PASSWORD);

            assertTrue(verified);

        } catch (Exception e) {
            fail(e);
        }
    }
}
