package com.steveq.app.persistence.service;

import com.steveq.app.persistence.dao.AuthoritiesRepository;
import com.steveq.app.persistence.dao.UserRepository;
import com.steveq.app.persistence.model.Authority;
import com.steveq.app.persistence.model.Password;
import com.steveq.app.persistence.model.SimpleUser;
import com.steveq.app.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService, UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByName(s);

        if(user == null){
            throw new UsernameNotFoundException(s);
        }

        return user;
    }

    @Override
    public User createUserFromSimpleUser(SimpleUser simpleUser) throws Exception{

        List<String> authoritiesNames = new ArrayList<>();
        authoritiesNames.add("STANDARD_USER");

        Set<Authority> newUserAuthorities =
                authoritiesRepository.findAuthoritiesByNameIn(authoritiesNames);

        User user = new User();
        user.setName(simpleUser.getName());
        user.setPass(new Password(simpleUser.getPassword()));
        user.setRoles(newUserAuthorities);

        System.out.println("ROLES :: " + user.getRoles());

        return user;
    }

    @Override
    public User saveUser(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getCurrentlyLoggedUser() {

        String currentlyLoggedName =
                (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.findByName(currentlyLoggedName);
    }
}
