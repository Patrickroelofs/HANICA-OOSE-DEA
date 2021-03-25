package com.spotitube.datasource.controller;

import com.spotitube.service.UserService;
import com.spotitube.service.dto.UserDTO;
import com.spotitube.datasource.dao.TokenDAO;
import com.spotitube.datasource.dao.UserDAO;
import com.spotitube.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private final String USERNAME = "patrick";
    private final String PASSWORD = "112112";
    private UserService userService;
    private UserDTO userDTO;
    private UserDAO userDAO;

    @BeforeEach
    public void setup() {
        userService = new UserService();

        userDTO = new UserDTO();
        userDTO.user = USERNAME;
        userDTO.password = PASSWORD;

        userDAO = mock(UserDAO.class);
        TokenDAO tokenDAO = mock(TokenDAO.class);

        userService.setUserDAO(userDAO);
        userService.setTokenDAO(tokenDAO);
    }
    
    @Test
    public void loginUserTest() {
        try {
            int statusCodeExpected = 200;

            User user = new User();
            user.setUser(USERNAME);
            user.setPassword(PASSWORD);

            when(userDAO.verifyUser(userDTO.user, userDTO.password)).thenReturn(true);

            Response response = userService.login(userDTO);

            assertEquals(statusCodeExpected, response.getStatus());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void loginUserTestFailsBecauseUserIsIncorrect() {
        try {
            int statusCodeExpected = 403;

            User user = new User();
            user.setUser(USERNAME);
            user.setPassword(PASSWORD);

            when(userDAO.verifyUser(userDTO.user, userDTO.password)).thenReturn(false);

            Response response = userService.login(userDTO);

            assertEquals(statusCodeExpected, response.getStatus());

        } catch (Exception e) {
            fail(e);
        }
    }
}
