package com.steveq.app.persistence.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GeoNoteRequest {

    @NotNull(message = "Please provide note")
    @Size(min = 1, max = 140, message = "Message must be between 1 and 140 chars")
    private String note;

    @NotNull(message = "Please provide latitude")
    private Double lat;

    @NotNull(message = "Please provide longitude")
    private Double lng;

    public GeoNoteRequest(){}

    public GeoNoteRequest(String note, Double lat, Double lng) {
        this.note = note;
        this.lat = lat;
        this.lng = lng;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "GeoNoteRequest{" +
                "note='" + note + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
