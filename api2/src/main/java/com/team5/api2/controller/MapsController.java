package com.team5.api2.controller;

import com.google.maps.model.DirectionsResult;
import com.team5.api2.dto.getDirectionsRequest;
import com.team5.api2.services.MapsServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapsController {

    @Autowired
    private MapsServices mapsService;

    @GetMapping ("/getDirections")
    public ResponseEntity<DirectionsResult> getDirections (@RequestBody getDirectionsRequest gdr){
        try {
            return ResponseEntity.ok().body(mapsService.getDirections(gdr.getLocationFrom(), gdr.getLocationTo()));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping ("/getJourneyCost")
    public ResponseEntity<String> getJourneyCost (){
        return ResponseEntity.ok().body("");
    }
}
