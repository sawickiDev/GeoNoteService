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

    private String owner;

    private String error;

    @NotNull(message = "Please provide expiration time for the note")
    private Integer expirationMinutes;

    private Long createdTimestamp;

    public GeoNoteRequest(){}

    public GeoNoteRequest(String note, Double lat, Double lng, Integer expirationMinutes) {
        this.note = note;
        this.lat = lat;
        this.lng = lng;
        this.expirationMinutes = expirationMinutes;
    }

    public GeoNoteRequest(String note, Double lat, Double lng, Integer expirationMinutes, String owner, Long createdTimestamp) {
        this.note = note;
        this.lat = lat;
        this.lng = lng;
        this.expirationMinutes = expirationMinutes;
        this.owner = owner;
        this.createdTimestamp = createdTimestamp;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getExpirationMinutes() {
        return expirationMinutes;
    }

    public void setExpirationMinutes(Integer expirationMinutes) {
        this.expirationMinutes = expirationMinutes;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Override
    public String toString() {
        return "GeoNoteRequest{" +
                "note='" + note + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", owner='" + owner + '\'' +
                ", error='" + error + '\'' +
                ", expirationMinutes=" + expirationMinutes +
                '}';
    }
}
