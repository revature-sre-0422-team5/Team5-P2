package com.team5.api2.controller;

import com.google.maps.model.DirectionsResult;
import com.team5.api2.dto.getDirectionsRequest;
import com.team5.api2.services.MapsServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Maps Controller
 */
@Slf4j
@RestController
public class MapsController {

    @Autowired
    private MapsServices mapsService;

    @GetMapping("/directions")
    public ResponseEntity<DirectionsResult> getDirections (@RequestBody getDirectionsRequest gdr){
        log.info("[GET] - Received directions request");
        if (gdr.getLocationFrom() == null || gdr.getLocationTo() == null || gdr.getLocationFrom().equals("") || gdr.getLocationTo().equals("")){
            log.warn("User input either null or empty strings for locations");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            log.info("Returning directions");
            return ResponseEntity.ok().body(mapsService.getDirections(gdr.getLocationFrom(), gdr.getLocationTo()));
        }
        catch (Exception e){
            log.error("ERROR at MapsController - getDirections");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping ("/getDistance")
    public ResponseEntity<Long> getDistance (@RequestBody getDirectionsRequest gdr){
        log.info("[GET] - Received distance request");
        if (gdr.getLocationFrom() == null || gdr.getLocationTo() == null || gdr.getLocationFrom().equals("") || gdr.getLocationTo().equals("")){
            log.warn("User input either null or empty strings for locations");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            log.info("Returning distance");
            return ResponseEntity.ok().body(mapsService.getDistance(gdr.getLocationFrom(), gdr.getLocationTo()));
        }
        catch (Exception e){
            log.error("ERROR at MapsController - getDirections");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
