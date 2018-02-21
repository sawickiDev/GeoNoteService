package com.steveq.app.persistence.service;

import com.steveq.app.persistence.dao.GeoNoteDao;
import com.steveq.app.persistence.model.GeoNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeoNoteServiceImpl implements GeoNoteService{

    @Autowired
    private GeoNoteDao geoNoteDao;

    @Override
    public Long save(GeoNote geoNote) {

        Long persistedId = -1L;
        try{
            geoNoteDao.save(geoNote);
        } catch (Exception ex){
            System.out.println("ERROR DURING GEONOTE SAVE :: " + ex);
        }

        return persistedId;
    }
}
