package com.steveq.app.persistence.model;

import com.steveq.app.persistence.dao.UserRepository;
import com.steveq.app.persistence.service.UserService;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Entity
@Component
@Table(name = "notes_table")
public class GeoNote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_seq")
    @SequenceGenerator(name = "note_seq", sequenceName = "note_seq")
    private Long id;

    @Column(name="note")
    private String note;


    @Column(name="created_date")
    private Timestamp createdDate;

    @Column(columnDefinition = "geometry")
    @Type(type="org.hibernate.spatial.GeometryType")
    private Point location;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Transient
    private String latLng;

    @Transient
    private String date;

    public GeoNote(){
    }

    public GeoNote(String note, String date, String latLng, User user) {
        System.out.println("ARGS :: " + note + " " + date + " " + latLng);
        this.note = note;
        this.createdDate = this.parseDateString(date);
        this.latLng = latLng;
        this.location = getPointFromCoordinates();

        this.owner = user;
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

    public String getLatLng() {
        if(latLng != null)
            return latLng;
        else
            return getLatLangFromPoint();
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
        if(date != null)
            return date;
        else
            return formatDateString(createdDate);
    }

    public void setDate(String date) {
        this.createdDate = this.parseDateString(date);
        this.date = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    private String getLatLangFromPoint(){

        StringBuilder builder = new StringBuilder();
        builder.append(this.getLocation().getX());
        builder.append(",");
        builder.append(this.getLocation().getY());

        return builder.toString();
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
                ", latLng='" + latLng + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
