package com.steveq.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/geonote-login")
    public String showGeonoteLogin(){
        return "custom-login";
    }
}
