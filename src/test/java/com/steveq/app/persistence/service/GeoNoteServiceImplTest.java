package com.steveq.app.persistence.service;

import com.steveq.app.persistence.dao.GeoNoteDao;
import com.steveq.app.persistence.model.GeoNote;
import com.steveq.app.persistence.model.GeoNoteRequest;
import com.steveq.app.persistence.model.Password;
import com.steveq.app.persistence.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GeoNoteServiceImplTest {

    @Mock
    private GeoNoteDao geoNoteDao;

    @Mock
    private UserService userService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Environment environment;

    @InjectMocks
    private GeoNoteServiceImpl geoNoteService;

//    @Test
//    public void save() {
//        ResponseEntity re = geoNoteService.save(new GeoNote());
//        verify(geoNoteDao).save(Mockito.any());
//        Assert.assertEquals(HttpStatus.CREATED, re.getStatusCode());
//    }

//    @Test
//    public void saveWithError() {
//        doThrow(DataIntegrityViolationException.class)
//                .when(geoNoteDao).save(Mockito.any());
//        ResponseEntity re = geoNoteService.save(new GeoNote());
//        verify(geoNoteDao).save(Mockito.any());
//        Assert.assertEquals(HttpStatus.CONFLICT, re.getStatusCode());
//    }

//    @Test
//    public void getOwned() {
//        List<GeoNote> ownedNotes = new ArrayList<>();
//        ownedNotes.add(new GeoNote("note1", new User("standard", new Password("standard")), 1.123, 2.412));
//        ownedNotes.add(new GeoNote("note3", new User("standard2", new Password("standard2")), 1.123, 2.412));
//        when(geoNoteDao.getAllByOwner(any()))
//                .thenReturn(ownedNotes);
//
//        List<GeoNoteRequest> resultNotes = geoNoteService.getOwned();
//        Assert.assertEquals(2, resultNotes.size());
//        Assert.assertEquals("note1", resultNotes.get(0).getNote());
//    }

//    @Test
//    public void getOwnedWithError() {
//        doThrow(DataAccessResourceFailureException.class)
//                .when(geoNoteDao)
//                .getAllByOwner(any());
//
//        List<GeoNoteRequest> resultNotes = geoNoteService.getOwned();
//        Assert.assertEquals(0, resultNotes.size());
//    }
//
//    @Test
//    public void getOther() {
//        List<GeoNote> otherNotes = new ArrayList<>();
//        otherNotes.add(new GeoNote("note1", new User("standard", new Password("standard")), 1.123, 2.412));
//        otherNotes.add(new GeoNote("note3", new User("standard2", new Password("standard2")), 1.123, 2.412));
//        when(geoNoteDao.getAllByOwnerIsNot(any()))
//                .thenReturn(otherNotes);
//
//        List<GeoNoteRequest> resultNotes = geoNoteService.getOther();
//        Assert.assertEquals(2, resultNotes.size());
//        Assert.assertEquals("note1", resultNotes.get(0).getNote());
//
//    }
//
//    @Test
//    public void getOtherWithError() {
//        doThrow(DataAccessResourceFailureException.class)
//                .when(geoNoteDao)
//                .getAllByOwnerIsNot(any());
//
//        List<GeoNoteRequest> resultNotes = geoNoteService.getOther();
//        Assert.assertEquals(0, resultNotes.size());
//    }

    @Test
    public void getOwnedInRadius() {
    }

    @Test
    public void getOtherInRadius() {
    }

    @Test
    public void noteIsSpam() {
    }
}