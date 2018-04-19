package com.steveq.app.persistence.dao;

import com.steveq.app.persistence.model.GeoNote;
import com.steveq.app.persistence.model.GeoNoteRequest;
import com.steveq.app.persistence.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeoNoteDao extends CrudRepository<GeoNote, Long>{
    List<GeoNote> getAllByOwnerAndActiveIsTrue(User user);
    List<GeoNote> findAllByOwnerNotAndActiveIsTrue(User user);
}
