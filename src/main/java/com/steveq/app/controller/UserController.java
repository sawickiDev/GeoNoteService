package com.steveq.app.controller;

import com.steveq.app.persistence.model.SimpleUser;
import com.steveq.app.persistence.model.User;
import com.steveq.app.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@RequestBody(required = true) SimpleUser user){
        System.out.println("X - simple user :: " + user);

        try {
            User newUser = userService.createUserFromSimpleUser(user);
            userService.saveUser(newUser);
            System.out.println("X - new user :: " + newUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
