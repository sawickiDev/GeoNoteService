package com.steveq.app.persistence.model;

import javax.validation.constraints.NotNull;

public class SimpleUser {

    @NotNull(message = "Please provide Username")
    private String name;

    @NotNull(message = "Please provide Password")
    private String password;

    public SimpleUser(){};

    public SimpleUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SimpleUser{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
