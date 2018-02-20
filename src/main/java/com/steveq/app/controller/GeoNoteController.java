package com.steveq.app.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/geonote")
public class GeoNoteController {

    @PostMapping(value = "/create")
    public String insertGeonote(@RequestBody String geoNote){
        System.out.println("GEONOTE :: " + geoNote);

        return "CORRECT";
    }

}
