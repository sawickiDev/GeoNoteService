package com.steveq.app.persistence.model;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Component
@Table(name = "notes_table")
public class GeoNote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "note_seq", allocationSize = 1)
    private Long id;

    @Column(name="note")
    private String note;


    @Column(name="created_date")
    private Timestamp createdDate;

    @Column(columnDefinition = "geometry")
    private Point location;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name="expiration_time_minutes")
    private Integer expirationTime;

    @Column(name="active")
    private Boolean active;

    public GeoNote(){
    }

    public GeoNote(String note, User user, Double lat, Double lng, Integer expirationTime) {
        this.note = note;
        this.createdDate = new Timestamp(new Date().getTime());
        this.location = getPointFromCoordinates(lat, lng);
        this.owner = user;
        this.expirationTime = expirationTime;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private Point getPointFromCoordinates(Double lat, Double lng){

        WKTReader reader = new WKTReader();
        Geometry geometry = null;
        String locationString = "POINT(" + lat + ' ' + lng + ")";
        try {
            geometry = reader.read(locationString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (Point)geometry;
    }

    private Timestamp parseDateString(String dateString){

        DateFormat df = new SimpleDateFormat("dd/mm/yyyy hh:mm");

        Date result = null;
        try {
            result = df.parse(dateString);
            return new Timestamp(result.getTime());
        } catch (java.text.ParseException e) {
            throw new NullPointerException("Error Parsing Date :: " + e);
        }
    }

    private String formatDateString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(date);
    }

    @Override
    public String toString() {
        return "GeoNote{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", createdDate=" + createdDate +
                ", location=" + location +
                ", owner=" + owner +
                ", expirationTime=" + expirationTime +
                ", active=" + active +
                '}';
    }
}
