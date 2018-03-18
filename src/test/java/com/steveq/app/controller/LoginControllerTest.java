package com.steveq.app.controller;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class LoginControllerTest {

    @Spy
    private LoginController loginController = new LoginController();

    @Test
    public void showGeonoteLogin() {
        Assert.assertEquals("custom-login", loginController.showGeonoteLogin());
    }
}