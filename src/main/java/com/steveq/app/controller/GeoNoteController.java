package com.steveq.app.controller;

import com.steveq.app.persistence.model.GeoNote;
import com.steveq.app.persistence.service.GeoNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/geonote")
public class GeoNoteController {

//    @Autowired
//    private GeoNoteService geoNoteService;
//
//    @PostMapping(value = "/create")
//    public String insertGeonote(@RequestBody GeoNote geoNote){
//        System.out.println("GEONOTE :: " + geoNote);
//
//        geoNoteService.save(geoNote);
//
//        return "CORRECT";
//    }

    @GetMapping(value = "/show")
    public String getGeonote(){

        return "HELLO GEONOTE";
    }

}
