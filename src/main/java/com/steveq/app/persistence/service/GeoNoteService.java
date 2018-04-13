package com.steveq.app.persistence.service;

import com.steveq.app.persistence.model.GeoNote;
import com.steveq.app.persistence.model.GeoNoteRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GeoNoteService {
    void save(GeoNote geoNote);
    List<GeoNoteRequest> getOwned();
    List<GeoNoteRequest> getOther();
    List<GeoNoteRequest> getOwnedInRadius(Double lat, Double lng, Double radius);
    List<GeoNoteRequest> getOtherInRadius(Double lat, Double lng, Double radius);
    Boolean noteIsSpam(Double lat, Double lng);
}
