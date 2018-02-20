package com.steveq.app.persistence.model;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "notes_table")
public class GeoNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="note")
    private String note;


    @Column(name="created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name="location")
    @Type(type="org.hibernate.spatial.GeometryType")
    private Point location;

    @Transient
    private String latLng;

    @Transient
    private String date;

    public GeoNote(){}

    public GeoNote(String note, String date, String latLng) {
        this.note = note;
        this.createdDate = this.parseDateString(date);
        this.latLng = latLng;

        this.location = getPointFromCoordinates();
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

    public void setCreatedDate(Date createdDate) {
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

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
        try {
            this.location = getPointFromCoordinates();
        } catch (RuntimeException rex) {
            System.out.println(rex);
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.createdDate = this.parseDateString(date);
        this.date = date;
    }

    private Point getPointFromCoordinates() throws RuntimeException{

        WKTReader reader = new WKTReader();
        Geometry geometry = null;
        String[] coordinates = this.latLng.split(",");
        String locationString = "POINT(" + coordinates[0] + ' ' + coordinates[1] + ")";
        try {
            geometry = reader.read(locationString);
        } catch (ParseException e) {
            throw new RuntimeException("Not a WKT string:" + locationString);
        }
        return (Point)geometry;
    }

    private Date parseDateString(String dateString){

        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");

        Date result = null;
        try {
            result = df.parse(dateString);
        } catch (java.text.ParseException e) {
            throw new NullPointerException("Error Parsing Date :: " + e);
        }

        return result;
    }

    @Override
    public String toString() {
        return "GeoNote{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", createdDate=" + createdDate +
                ", location=" + location +
                ", latLng='" + latLng + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
