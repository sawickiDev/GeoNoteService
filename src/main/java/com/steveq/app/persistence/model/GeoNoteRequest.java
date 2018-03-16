package com.steveq.app.persistence.model;

public class GeoNoteRequest {
    private String note;
    private String date;
    private String latLng;

    public GeoNoteRequest(){}

    public GeoNoteRequest(String note, String date, String latLng) {
        this.note = note;
        this.date = date;
        this.latLng = latLng;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "GeoNoteRequest{" +
                "note='" + note + '\'' +
                ", date='" + date + '\'' +
                ", latLng='" + latLng + '\'' +
                '}';
    }
}
