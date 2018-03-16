package com.steveq.app.persistence.service;

import com.steveq.app.persistence.model.GeoNote;
import com.steveq.app.persistence.model.GeoNoteRequest;

import java.util.List;

public interface GeoNoteService {
    public Long save(GeoNote geoNote);
    public List<GeoNoteRequest> getOwned();
    public List<GeoNoteRequest> getOther();
    public List<GeoNoteRequest> getOwnedInRadius(String lat, String lng, String radius);
}
