package com.steveq.app.controller;

import com.steveq.app.persistence.model.GeoNote;
import com.steveq.app.persistence.model.GeoNoteRequest;
import com.steveq.app.persistence.service.GeoNoteService;
import com.steveq.app.persistence.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/geonote")
public class GeoNoteController {

    @Autowired
    private GeoNoteService geoNoteService;

    @Autowired
    private UserDetailsService userService;

    @PostMapping(value = "/create")
    public ResponseEntity insertGeonote(@RequestBody GeoNoteRequest geoNoteRequest){

        try{
            GeoNote geoNote =
                    new GeoNote(geoNoteRequest.getNote(), geoNoteRequest.getDate(), geoNoteRequest.getLatLng(), userService.getCurrentlyLoggedUser());

            geoNoteService.save(geoNote);
            return new ResponseEntity<String>("Note Created Successfully", HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<String>("Error Creating Note", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/fetch")
    public List<GeoNoteRequest> getGeonote(@RequestParam String lat,
                                           @RequestParam String lng,
                                           @RequestParam String radius){

        List<GeoNoteRequest> availableGeonotes = new ArrayList<>();

        try {
            availableGeonotes.addAll(geoNoteService.getOwned());
            geoNoteService.getOwnedInRadius(lat, lng, radius);
        } catch (Exception ex){
            //unauthorized
            ex.printStackTrace();
        }

        try{
            availableGeonotes.addAll(geoNoteService.getOther());
        } catch (Exception ex){
            //unauthorized
            ex.printStackTrace();
        }

        return availableGeonotes;
    }

}
