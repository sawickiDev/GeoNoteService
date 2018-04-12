package com.steveq.app.persistence.model;

import java.util.List;

public class GeonoteBatch {
    private List<GeoNoteRequest> notes;

    public GeonoteBatch() {}

    public GeonoteBatch(List<GeoNoteRequest> notes) {
        this.notes = notes;
    }

    public List<GeoNoteRequest> getNotes() {
        return notes;
    }

    public void setNotes(List<GeoNoteRequest> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "GeonoteBatch{" +
                "notes=" + notes +
                '}';
    }
}
