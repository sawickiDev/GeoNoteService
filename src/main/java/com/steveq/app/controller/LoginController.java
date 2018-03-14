package com.steveq.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showGeonoteLogin")
    public String showGeonoteLogin(){
        return "custom-login";
    }
}
