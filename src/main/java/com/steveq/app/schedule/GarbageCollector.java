package com.steveq.app.schedule;

import com.steveq.app.persistence.model.GeoNote;
import com.steveq.app.persistence.service.GeoNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GarbageCollector {

    @Autowired
    public GeoNoteService geoNoteService;

    @Scheduled(fixedDelay = 60000)
    public void clearExpiredNotes() {
        List<GeoNote> expired = geoNoteService.getExpiredNotes();
        geoNoteService.inactivateNotes(expired);
    }

}
