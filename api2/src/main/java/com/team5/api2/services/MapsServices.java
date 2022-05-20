package com.team5.api2.services;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

//Service just wraps Google Maps APIs
@Service("Maps")
@Slf4j
public class MapsServices {

    private GeoApiContext context;

    @Autowired
    public MapsServices(@Value("${api.googledirectionskey}") String mapsApiKey) {
        this.context = new GeoApiContext.Builder()
        .apiKey(mapsApiKey)
        .build();
    }

    public DirectionsResult getDirections (String directionFrom, String directionTo){
        try {
            log.info("Calling directions api");
            return DirectionsApi.getDirections(context, directionFrom, directionTo).await();
        }
        catch (Exception e){
            log.error("Something went wrong calling the directions api");
            e.printStackTrace();
            return null;
        }
    }

    public long getDistance (String locationFrom, String locationTo){
        try {
            log.info("Calling distance matrix api");
            return DistanceMatrixApi.getDistanceMatrix(context, new String[]{locationFrom},new String[]{locationTo}).await()
            .rows[0].elements[0]
            .distance.inMeters;
        }
        catch (Exception e){
            log.error("Something went wrong calling the distance matrix api");
            e.printStackTrace();
            return Long.MAX_VALUE;
        }
    }

}
