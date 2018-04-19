package com.steveq.app.persistence.service;

import com.steveq.app.persistence.dao.GeoNoteDao;
import com.steveq.app.persistence.model.GeoNote;
import com.steveq.app.persistence.model.GeoNoteRequest;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource({"classpath:geonote.properties"})
public class GeoNoteServiceImpl implements GeoNoteService{

    @Autowired
    private GeoNoteDao geoNoteDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Environment environment;

    @Override
    public void save(GeoNote geoNote) throws DataIntegrityViolationException{
        geoNoteDao.save(geoNote);
    }

    @Override
    @PreAuthorize("#oauth2.hasScope('read_note') and hasAuthority('READ_OWNED')")
    public List<GeoNoteRequest> getOwned() {

        List<GeoNoteRequest> ownedNotes = new ArrayList<>();
        try{
            ownedNotes =
                    mapGeonoteToRequestValues(geoNoteDao.getAllByOwnerAndActiveIsTrue(userService.getCurrentlyLoggedUser()));
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return ownedNotes;
    }

    @Override
    @PreAuthorize("#oauth2.hasScope('read_note') and hasAuthority('READ_OTHER')")
    public List<GeoNoteRequest> getOther() {
        List<GeoNoteRequest> otherNotes = new ArrayList<>();
        try{
            otherNotes =
                    mapGeonoteToRequestValues(geoNoteDao.findAllByOwnerNotAndActiveIsTrue(userService.getCurrentlyLoggedUser()));
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return otherNotes;
    }

    @Override
    @Transactional
    @PreAuthorize("#oauth2.hasScope('read_note') and hasAuthority('READ_OWNED')")
    public List<GeoNoteRequest> getOwnedInRadius(Double lat, Double lng, Double radius) {

        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("Select gn From GeoNote as gn Where dwithin(gn.location , :center, :radius) = true And gn.owner = :currentUser");
        query.setParameter("center", convertCoordsToGeometry(lat, lng));
        query.setParameter("radius", radius);
        query.setParameter("currentUser", userService.getCurrentlyLoggedUser());

        List<GeoNote> results = (List<GeoNote>)query.getResultList();

        return mapGeonoteToRequestValues(results);
    }

    @Override
    @Transactional
    @PreAuthorize("#oauth2.hasScope('read_note') and hasAuthority('READ_OTHER')")
    public List<GeoNoteRequest> getOtherInRadius(Double lat, Double lng, Double radius) {

        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("Select gn From GeoNote as gn Where dwithin(gn.location , :center, :radius) = true And gn.owner != :currentUser");
        query.setParameter("center", convertCoordsToGeometry(lat, lng));
        query.setParameter("radius", radius);
        query.setParameter("currentUser", userService.getCurrentlyLoggedUser());

        List<GeoNote> results = (List<GeoNote>)query.getResultList();

        return mapGeonoteToRequestValues(results);
    }

    @Override
    @Transactional
    public Boolean noteIsSpam(Double lat, Double lng) {
        List<GeoNoteRequest> notesInSpamRadius = getOwnedInRadius(lat, lng, Double.valueOf(environment.getProperty("geonote.spam_radius")));
        return notesInSpamRadius.size() >= Integer.valueOf(environment.getProperty("geonote.spam_count"));
    }

    @Transactional
    @Override
    public List<GeoNote> getExpiredNotes() {
        Session session = entityManager.unwrap(Session.class);

        Query expiredNotesQuery = session.createNativeQuery
                (
                    "Select * from notes_table "
                    + "where (cast(extract(epoch from created_date) as integer) + expiration_time_minutes) <= (cast(extract(epoch from current_timestamp) as integer));"
                ).addEntity(GeoNote.class);

        List<GeoNote> results = (List<GeoNote>)expiredNotesQuery.getResultList();
        System.out.println("EXPIRED NOTES :: " + results);

        return results;
    }

    @Override
    public void inactivateNotes(List<GeoNote> notes) {

        notes.forEach(n -> n.setActive(false));

        geoNoteDao.saveAll(notes);
    }

    @Override
    public List<GeoNoteRequest> mapGeonoteToRequestValues(List<GeoNote> geoNotes){

        List<GeoNoteRequest> geoNoteRequests = new ArrayList<>();
        for(GeoNote geoNote : geoNotes){
            GeoNoteRequest geoNoteRequest = new GeoNoteRequest(geoNote.getNote(), geoNote.getLocation().getX(), geoNote.getLocation().getY(), geoNote.getExpirationTime(), geoNote.getOwner().getName());
            geoNoteRequests.add(geoNoteRequest);
        }

        return geoNoteRequests;
    }

    private Geometry convertCoordsToGeometry(Double lat, Double lng){
        GeometryFactory gf = new GeometryFactory();
        return gf.createPoint(new Coordinate(lat, lng));
    }
}
