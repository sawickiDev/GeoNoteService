package com.steveq.app.persistence.service;

import com.steveq.app.persistence.dao.ClientRepository;
import com.steveq.app.persistence.model.OauthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
class OauthClientDetailsService implements ClientDetailsService{

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {

        OauthClient client = clientRepository.findByName(s);

        if(client == null){
            throw new UsernameNotFoundException(s);
        }

        return client;
    }
}
