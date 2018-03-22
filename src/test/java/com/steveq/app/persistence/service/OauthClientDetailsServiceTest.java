package com.steveq.app.persistence.service;

import com.steveq.app.persistence.dao.ClientRepository;
import com.steveq.app.persistence.model.OauthClient;
import com.steveq.app.persistence.model.Password;
import com.steveq.app.persistence.model.Scope;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OauthClientDetailsServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    OauthClientDetailsService oauthClientDetailsService;

    @Test
    public void loadClientByClientId() {
        Set<Scope> scopes = new HashSet<>();
        scopes.add(new Scope("scope1"));

        OauthClient oauthClient = new OauthClient();
        oauthClient.setId(1);
        oauthClient.setName("clien");
        oauthClient.setSecret("client");
        oauthClient.setScopes(scopes);

        when(clientRepository.findByName(any()))
                .thenReturn(oauthClient);

        ClientDetails client = oauthClientDetailsService.loadClientByClientId("name");

        Assert.assertEquals(true, client.getScope().contains("scope1"));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadClientByClientIdWithNull() {
        Set<Scope> scopes = new HashSet<>();
        scopes.add(new Scope("scope1"));

        OauthClient oauthClient = new OauthClient();
        oauthClient.setId(1);
        oauthClient.setName("clien");
        oauthClient.setSecret("client");
        oauthClient.setScopes(scopes);

        when(clientRepository.findByName(any()))
                .thenReturn(null);

        oauthClientDetailsService.loadClientByClientId("name");
    }
}