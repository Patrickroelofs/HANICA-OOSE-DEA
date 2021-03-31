package com.spotitube.datasource.dao;

import com.spotitube.datasource.dao.sql.UserDAO;
import com.spotitube.exceptions.SQLServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    private final String USERNAME = "patrick";
    private final String PASSWORD = "112112";

    private UserDAO userDAO;
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    @BeforeEach
    public void setup() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        userDAO = new UserDAO();
        userDAO.setDataSource(dataSource);
    }

    @Test
    public void loginWithValidUsernameAndPassword() {
        try {
            // ARRANGE
            String expectedSQL = "SELECT * FROM users WHERE username = ? AND password = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);

            userDAO.setDataSource(dataSource);

            // ACT
            boolean verified = userDAO.verifyUser(USERNAME, PASSWORD);

            // ASSERT
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, USERNAME);
            verify(preparedStatement).setString(2, PASSWORD);

            assertTrue(verified);

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void loginWithValidUsernameAndPasswordThrowsInternalServerErrorTest() {
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenThrow(new SQLException());

            assertThrows(SQLServerException.class, () -> userDAO.verifyUser(USERNAME, PASSWORD));
        } catch (Exception e) {
            fail(e);
        }
    }
}
