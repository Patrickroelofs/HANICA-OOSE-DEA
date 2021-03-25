package com.spotitube.datasource.dao;

import com.spotitube.domain.Token;
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

public class TokenDAOTest {

    private final String USERNAME = "patrick";
    private final String TOKEN = "111-111-111";

    private TokenDAO tokenDAO;
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

        tokenDAO = new TokenDAO();
        tokenDAO.setDataSource(dataSource);
    }

    @Test
    public void createTokenTest() {
        try {
            // ARRANGE
            String sql = "UPDATE users SET token = ? WHERE username = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            when(resultSet.next()).thenReturn(true);

            // ACT
            Token token = tokenDAO.insert(USERNAME);


            // ASSERT
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setString(1, token.getToken());
            verify(preparedStatement).setString(2, token.getUser());

            assertEquals(USERNAME, token.getUser());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void createTokenThrowsInternalServerErrorTest() {
        try {
            String sql = "UPDATE users SET token = ? WHERE username = ?";
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

            assertThrows(SQLServerException.class, () -> tokenDAO.insert(USERNAME));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void verifyTokenTest() {
        try {
            // ARRANGE
            String sql = "SELECT * FROM users WHERE token = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);

            // ACT
            boolean verified = tokenDAO.verify(TOKEN);

            // ASSERT
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setString(1, TOKEN);

            assertTrue(verified);


        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void verifyTokenThrowsInternalServerErrorTest() {
        try {
            String sql = "SELECT * FROM users WHERE token = ?";
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenThrow(new SQLException());

            assertThrows(SQLServerException.class, () -> tokenDAO.verify(TOKEN));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getUsernameTest() {
        try {
            // ARRANGE
            String sql = "SELECT username FROM users WHERE token = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString(1)).thenReturn(USERNAME);

            // ACT
            String test = tokenDAO.getUsername(TOKEN);

            // ASSERT
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setString(1, TOKEN);

            assertEquals(USERNAME, test);


        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getUsernameReturnsNull() {
        try {
            String sql = "SELECT username FROM users WHERE token = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            when(resultSet.getString(1)).thenReturn(USERNAME);

            String test = tokenDAO.getUsername(TOKEN);

            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setString(1, TOKEN);

            assertNull(test);

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getUsernameThrowsInternalServerErrorTest() {
        try {
            String sql = "SELECT username FROM users WHERE token = ?";
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenThrow(new SQLException());

            assertThrows(SQLServerException.class, () -> tokenDAO.getUsername(TOKEN));
        } catch (Exception e) {
            fail(e);
        }
    }
}
