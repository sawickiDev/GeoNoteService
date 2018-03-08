package com.steveq.app.persistence.service;

import com.steveq.app.persistence.model.SimpleUser;
import com.steveq.app.persistence.model.User;

public interface UserService {
    User createUserFromSimpleUser(SimpleUser simpleUser) throws Exception;
    User saveUser(User user) throws Exception;
}
