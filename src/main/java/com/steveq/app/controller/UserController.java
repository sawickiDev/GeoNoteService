package com.steveq.app.controller;

import com.steveq.app.persistence.model.SimpleUser;
import com.steveq.app.persistence.model.User;
import com.steveq.app.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public HttpStatus registerUser(@Valid @RequestBody SimpleUser user){

        User newUser = userService.createUserFromSimpleUser(user);

        try{
            userService.saveUser(newUser);
        } catch (DataIntegrityViolationException dve){
            return HttpStatus.CONFLICT;
        }

        return HttpStatus.OK;
    }

}
