package com.steveq.app.persistence.dao;

import com.steveq.app.persistence.model.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthoritiesRepository extends CrudRepository<Authority, Integer>{
    Set<Authority> findAuthoritiesByNameIn(List<String> names);
}
