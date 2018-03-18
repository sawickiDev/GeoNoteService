package com.steveq.app.controller;

import com.steveq.app.persistence.model.GeoNoteRequest;
import com.steveq.app.persistence.model.Password;
import com.steveq.app.persistence.model.User;
import com.steveq.app.persistence.service.GeoNoteService;
import com.steveq.app.persistence.service.UserDetailsService;
import com.steveq.app.persistence.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GeoNoteControllerTest {

    @Mock
    private GeoNoteService geoNoteService;

    @Mock
    private UserService userDetailsService;

    @Mock
    private Environment environment;

    @InjectMocks
    private GeoNoteController geoNoteController;

    private List<GeoNoteRequest> ownedGeonotes = new ArrayList<>();
    private List<GeoNoteRequest> otherGeonotes = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ownedGeonotes.add(new GeoNoteRequest("note1", 1.23, 2.34));
        ownedGeonotes.add(new GeoNoteRequest("note2", 1.23, 2.34));
        ownedGeonotes.add(new GeoNoteRequest("note3", 1.23, 2.34));

        otherGeonotes.add(new GeoNoteRequest("noteo1", 1.23, 2.34));
        otherGeonotes.add(new GeoNoteRequest("noteo2", 1.23, 2.34));
    }

    @Test
    public void insertGeonoteCorrectParams() {
        GeoNoteRequest geoNoteRequest = new GeoNoteRequest();
        geoNoteRequest.setNote("My Test Note");
        geoNoteRequest.setLat(51.786734);
        geoNoteRequest.setLng(19.435258);
        when(geoNoteService.noteIsSpam(geoNoteRequest.getLat(), geoNoteRequest.getLng()))
                .thenReturn(false);
        when(userDetailsService.getCurrentlyLoggedUser())
                .thenReturn(new User("testUser", new Password("testUser")));
        when(environment.getProperty("geonote.create_ok"))
                .thenReturn("Note created");

        ResponseEntity responseEntity = geoNoteController.insertGeonote(geoNoteRequest);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(geoNoteService).save(any());
    }

    @Test
    public void insertGeonoteSpam() {
        GeoNoteRequest geoNoteRequest = new GeoNoteRequest();
        geoNoteRequest.setNote("My Spam Note");
        geoNoteRequest.setLat(51.786734);
        geoNoteRequest.setLng(19.435258);

        when(geoNoteService.noteIsSpam(geoNoteRequest.getLat(), geoNoteRequest.getLng()))
                .thenReturn(true);
        when(userDetailsService.getCurrentlyLoggedUser())
                .thenReturn(new User("testUser", new Password("testUser")));
        when(environment.getProperty("geonote.spam"))
                .thenReturn("Too much notes in this area");

        ResponseEntity responseEntity = geoNoteController.insertGeonote(geoNoteRequest);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        verify(geoNoteService, never()).save(any());
    }

    @Test
    public void insertGeonoteExceptionWhenInsert() {
        GeoNoteRequest geoNoteRequest = new GeoNoteRequest();
        geoNoteRequest.setNote("My Spam Note");
        geoNoteRequest.setLat(51.786734);
        geoNoteRequest.setLng(19.435258);

        when(geoNoteService.noteIsSpam(geoNoteRequest.getLat(), geoNoteRequest.getLng()))
                .thenReturn(false);
        when(userDetailsService.getCurrentlyLoggedUser())
                .thenReturn(new User("testUser", new Password("testUser")));
        when(environment.getProperty("geonote.spam"))
                .thenReturn("Too much notes in this area");
        doThrow(new DataIntegrityViolationException("Error creating note"))
                .when(geoNoteService)
                .save(any());

        ResponseEntity responseEntity = geoNoteController.insertGeonote(geoNoteRequest);
        Assert.assertEquals(HttpStatus.EXPECTATION_FAILED, responseEntity.getStatusCode());
    }

    @Test
    public void viewOwnedGeonotes() {
        when(geoNoteService.getOwned())
                .thenReturn(ownedGeonotes);
        List<GeoNoteRequest> ownedNotes =
                geoNoteController.viewOwnedGeonotes();

        Assert.assertEquals(3, ownedNotes.size());
    }

    @Test
    public void getGeonoteCorrectParams() {
        when(geoNoteService.getOwnedInRadius(any(), any(), any()))
                .thenReturn(ownedGeonotes);
        when(geoNoteService.getOtherInRadius(any(), any(), any()))
                .thenReturn(otherGeonotes);

        ResponseEntity response = geoNoteController.getGeonote(1.23, 2.34, 200.0, "owned,others");

        Assert.assertEquals(5, ((List<GeoNoteRequest>)response.getBody()).size());
    }

    @Test
    public void getGeonoteWithoutAccess() {
        when(geoNoteService.getOwnedInRadius(any(), any(), any()))
                .thenReturn(ownedGeonotes);
        when(geoNoteService.getOtherInRadius(any(), any(), any()))
                .thenReturn(otherGeonotes);

        ResponseEntity response = geoNoteController.getGeonote(1.23, 2.34, 200.0, null);

        Assert.assertEquals(3, ((List<GeoNoteRequest>)response.getBody()).size());
    }
}