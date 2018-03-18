package com.steveq.app.controller;

import com.steveq.app.persistence.model.SimpleUser;
import com.steveq.app.persistence.model.User;
import com.steveq.app.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> registerUser(@RequestBody SimpleUser user){
        User newUser = userService.createUserFromSimpleUser(user);

        try{
            userService.saveUser(newUser);
        } catch (DataIntegrityViolationException dve){
            return new ResponseEntity<>("duplicate user", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("user registered", HttpStatus.OK);
    }

}
