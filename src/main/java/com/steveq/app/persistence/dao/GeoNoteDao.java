package com.steveq.app.persistence.dao;

import com.steveq.app.persistence.model.GeoNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoNoteDao extends CrudRepository<GeoNote, Long>{

}
