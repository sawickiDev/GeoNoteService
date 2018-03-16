package com.steveq.app.persistence.service;

import com.steveq.app.persistence.dao.GeoNoteDao;
import com.steveq.app.persistence.model.GeoNote;
import com.steveq.app.persistence.model.GeoNoteRequest;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class GeoNoteServiceImpl implements GeoNoteService{

    @Autowired
    private GeoNoteDao geoNoteDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Long save(GeoNote geoNote) {

        Long persistedId = -1L;
        try{
            geoNoteDao.save(geoNote);
        } catch (Exception ex){
            System.out.println("ERROR DURING GEONOTE SAVE :: " + ex);
        }

        return persistedId;
    }

    @Override
    @PreAuthorize("#oauth2.hasScope('read_note') and hasAuthority('READ_OWNED')")
    public List<GeoNoteRequest> getOwned() {
        System.out.println("AUTHORITIES :: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        List<GeoNoteRequest> ownedNotes = new ArrayList<>();
        try{
            List<GeoNote> geoNotes = geoNoteDao.getAllByOwner(userService.getCurrentlyLoggedUser());
            System.out.println("OWNED :: " + geoNotes);
            for(GeoNote geoNote : geoNotes){
                GeoNoteRequest geoNoteRequest = new GeoNoteRequest(geoNote.getNote(), geoNote.getDate(), geoNote.getLatLng());
                ownedNotes.add(geoNoteRequest);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println("OWNED :: " + ownedNotes);
        return ownedNotes;
    }

    @Override
    @PreAuthorize("#oauth2.hasScope('read_note') and hasAuthority('READ_OTHER')")
    public List<GeoNoteRequest> getOther() {
        List<GeoNoteRequest> otherNotes = new ArrayList<>();
        try{
            List<GeoNote> geoNotes = geoNoteDao.getAllByOwnerIsNot(userService.getCurrentlyLoggedUser());

            System.out.println("OTHER :: " + geoNotes);
            for(GeoNote geoNote : geoNotes){
                GeoNoteRequest geoNoteRequest = new GeoNoteRequest(geoNote.getNote(), geoNote.getDate(), geoNote.getLatLng());
                otherNotes.add(geoNoteRequest);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        System.out.println("OTHER :: " + otherNotes);
        return otherNotes;
    }

    @Override
    @Transactional
    @PreAuthorize("#oauth2.hasScope('read_note') and hasAuthority('READ_OWNED')")
    public List<GeoNoteRequest> getOwnedInRadius(String lat, String lng, String radius) {
        Session session = entityManager.unwrap(Session.class);
        System.out.println("GET OWNED IN RADIUS :: " + lat + " " + lng + " " + radius);
        Query query = session.createQuery("From GeoNote as gn Where within(:geomPointgn.location , :circle) = true");
        query.setParameter("circle", createCircle(Double.valueOf(lat), Double.valueOf(lng), Double.valueOf(radius)));
        List results = query.getResultList();
        System.out.println("GIS QUERY RESULT :: " + results);
        return null;
    }

    public Geometry createCircle(double x, double y, double radius) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(32);
        shapeFactory.setCentre(new Coordinate(x, y));
        shapeFactory.setSize(radius * 2);
        return shapeFactory.createCircle();
    }
}
