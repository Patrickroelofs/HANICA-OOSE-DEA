package com.spotitube.datasource.controller;

import com.spotitube.controller.UserController;
import com.spotitube.controller.dto.UserDTO;
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
    private String USERNAME = "patrick";
    private String PASSWORD = "112112";
    private UserController userController;
    private UserDTO userDTO;
    
    @BeforeEach
    public void setup() {
        userController = new UserController();

        userDTO = new UserDTO();
        userDTO.user = USERNAME;
        userDTO.password = PASSWORD;
    }
    
    @Test
    public void loginUserTest() {
        try {
            int statusCodeExpected = 200;

            User user = new User();
            user.setUser(USERNAME);
            user.setPassword(PASSWORD);

            UserDAO userDAO = mock(UserDAO.class);
            TokenDAO tokenDAO = mock(TokenDAO.class);
            when(userDAO.verifyUser(userDTO.user, userDTO.password)).thenReturn(true);

            userController.setUserDAO(userDAO);
            userController.setTokenDAO(tokenDAO);

            Response response = userController.login(userDTO);

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

            UserDAO userDAO = mock(UserDAO.class);
            TokenDAO tokenDAO = mock(TokenDAO.class);
            when(userDAO.verifyUser(userDTO.user, userDTO.password)).thenReturn(false);

            userController.setUserDAO(userDAO);
            userController.setTokenDAO(tokenDAO);

            Response response = userController.login(userDTO);

            assertEquals(statusCodeExpected, response.getStatus());

        } catch (Exception e) {
            fail(e);
        }
    }
}
