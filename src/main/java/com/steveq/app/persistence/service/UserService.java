package com.steveq.app.persistence.service;

import com.steveq.app.persistence.model.SimpleUser;
import com.steveq.app.persistence.model.User;

public interface UserService {
    User createUserFromSimpleUser(SimpleUser simpleUser);
    User saveUser(User user);
    User findByName(String name);
    User getCurrentlyLoggedUser();
}
