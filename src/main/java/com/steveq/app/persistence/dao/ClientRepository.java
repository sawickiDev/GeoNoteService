package com.steveq.app.persistence.dao;

import com.steveq.app.persistence.model.OauthClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<OauthClient, Integer> {
    OauthClient findByName(String name);
}
