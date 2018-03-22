package com.steveq.app.persistence.service;

import com.steveq.app.persistence.dao.AuthoritiesRepository;
import com.steveq.app.persistence.dao.UserRepository;
import com.steveq.app.persistence.model.Authority;
import com.steveq.app.persistence.model.Password;
import com.steveq.app.persistence.model.SimpleUser;
import com.steveq.app.persistence.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthoritiesRepository authoritiesRepository;

    @InjectMocks
    private UserDetailsService userDetailsService;

    @Test
    public void loadUserByUsername() {
        when(userRepository.findByName(any()))
                .thenReturn(new User("user1", new Password("user1")));

        Assert.assertEquals("user1", userDetailsService.findByName("name").getName());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameWithException() {
        when(userRepository.findByName(any()))
                .thenReturn(null);

        userDetailsService.loadUserByUsername("name");
    }

    @Test
    public void createUserFromSimpleUser() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority("STANDARD_USER"));
        authorities.add(new Authority("READ_OWNED"));
        authorities.add(new Authority("READ_OTHERS"));
        when(authoritiesRepository.findAuthoritiesByNameIn(any()))
                .thenReturn(authorities);

        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setName("user1");
        simpleUser.setPassword("user1");

        User resultUser = userDetailsService.createUserFromSimpleUser(simpleUser);

        Assert.assertEquals("user1", resultUser.getName());
        Assert.assertEquals(3, resultUser.getAuthorities().size());
    }

//    @Test
//    public void saveUser() {
//        userDetailsService.saveUser(new User());
//        verify(userRepository).save(any());
//    }

    @Test
    public void findByName() {
        userDetailsService.findByName("name");
        verify(userRepository).findByName(any());
    }

    @Test
    public void getCurrentlyLoggedUser() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication())
                .thenReturn(authentication);
        when(authentication.getPrincipal())
                .thenReturn("name");
        SecurityContextHolder.setContext(securityContext);
        userDetailsService.getCurrentlyLoggedUser();
        verify(userRepository).findByName(any());
    }
}