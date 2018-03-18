package com.steveq.app.controller;

import com.steveq.app.persistence.model.GeoNote;
import com.steveq.app.persistence.model.GeoNoteRequest;
import com.steveq.app.persistence.service.GeoNoteService;
import com.steveq.app.persistence.service.UserDetailsService;
import com.steveq.app.persistence.service.UserService;
import com.steveq.app.validation.RadiusSize;
import com.steveq.app.validation.RadiusValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/geonote")
@PropertySource({"classpath:geonote.properties"})
@Validated
public class GeoNoteController {

    @Autowired
    private GeoNoteService geoNoteService;

    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    @PostMapping(value = "/create")
    public ResponseEntity<String> insertGeonote(@Valid @RequestBody GeoNoteRequest geoNoteRequest){

        if(geoNoteService.noteIsSpam(geoNoteRequest.getLat(), geoNoteRequest.getLng())){
            return new ResponseEntity<>(environment.getProperty("geonote.spam"), HttpStatus.CONFLICT);
        }
        GeoNote geoNote =
                new GeoNote(geoNoteRequest.getNote(), userService.getCurrentlyLoggedUser(), geoNoteRequest.getLat(), geoNoteRequest.getLng());

        try{
            geoNoteService.save(geoNote);
        } catch (DataIntegrityViolationException dve){
            return new ResponseEntity<>("Error during contact creation", HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(environment.getProperty("geonote.create_ok"), HttpStatus.OK);

    }

    @GetMapping(value = "/preview")
    public List<GeoNoteRequest> viewOwnedGeonotes(){
        return geoNoteService.getOwned();
    }

    @GetMapping(value = "/fetch")
    public ResponseEntity<List<GeoNoteRequest>> getGeonote(@RequestParam Double lat,
                                           @RequestParam Double lng,
                                           @Valid @RadiusSize @RequestParam Double radius,
                                           @RequestParam(required = false) String access){

        List<String> accessLevels = fetchAccessLevels(access);
        List<GeoNoteRequest> availableGeonotes = new ArrayList<>();

        if(accessLevels.contains("owned")){
            availableGeonotes.addAll(geoNoteService.getOwnedInRadius(lat, lng, radius));
        }

        if(accessLevels.contains("others")){
            availableGeonotes.addAll(geoNoteService.getOtherInRadius(lat, lng, radius));
        }

        return new ResponseEntity<>(availableGeonotes, HttpStatus.OK);
    }

    private List<String> fetchAccessLevels(String providedAccessLevel){

        List<String> accessLevels = new ArrayList<>();

        if(providedAccessLevel == null || providedAccessLevel.isEmpty())
            accessLevels.add("owned");
        else
            accessLevels = Arrays.asList(providedAccessLevel.split(","));

        return accessLevels;
    }

}
