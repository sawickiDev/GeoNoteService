package com.steveq.app.persistence.model;

public class SimpleUser {

    private String name;
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
