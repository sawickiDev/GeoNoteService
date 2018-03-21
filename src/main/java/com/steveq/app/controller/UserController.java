package com.steveq.app.controller;

import com.steveq.app.persistence.model.SimpleUser;
import com.steveq.app.persistence.model.User;
import com.steveq.app.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/register-form")
    public String showRegisterForm(ModelMap modelMap){
        modelMap.addAttribute("simpleUser", new SimpleUser());
        return "self-register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@Valid @ModelAttribute("simpleUser") SimpleUser user,
                               BindingResult bindingResult){
        System.out.println("SIMPLE USER :: " + user);
        if(bindingResult.hasErrors()){
            return "self-register";
        }
        User newUser = userService.createUserFromSimpleUser(user);

        try{
            userService.saveUser(newUser);
        } catch (DataIntegrityViolationException dve){
            return "self-register";
        }

        return "redirect:/geonote-login";
    }

}
