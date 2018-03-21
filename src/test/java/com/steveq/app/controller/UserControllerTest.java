package com.steveq.app.controller;

import com.steveq.app.persistence.model.Password;
import com.steveq.app.persistence.model.SimpleUser;
import com.steveq.app.persistence.model.User;
import com.steveq.app.persistence.service.UserDetailsService;
import com.steveq.app.persistence.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userDetailsService;

    @InjectMocks
    private UserController userController;

    @Test
    public void registerUser() {

//        when(userDetailsService.createUserFromSimpleUser(any(SimpleUser.class)))
//                .thenReturn(new User("user1", new Password("user1")));
//
//        String result = userController.registerUser(new SimpleUser("user1", "user1"), null);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void registerExistingUser() {

//        when(userDetailsService.createUserFromSimpleUser(any(SimpleUser.class)))
//                .thenReturn(new User("user1", new Password("user1")));
//
//        doThrow(DataIntegrityViolationException.class)
//                .when(userDetailsService)
//                .saveUser(any());
//        ResponseEntity result = userController.registerUser(new SimpleUser("user1", "user1"));
//        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}