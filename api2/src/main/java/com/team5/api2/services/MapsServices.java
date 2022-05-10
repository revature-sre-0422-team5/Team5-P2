package com.team5.api2.services;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MapsServices {

    @Value("${api.googledirectionskey}")
    private String mapsApiKey;        

    public DirectionsResult getDirections (String directionFrom, String directionTo){
        try {
            GeoApiContext context = new GeoApiContext.Builder()
            .apiKey(mapsApiKey)
            .build();

            DirectionsResult results = DirectionsApi.getDirections(context, directionFrom, directionTo).await();
            return results;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
