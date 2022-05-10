package com.team5.api2.services;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MapsServices {

    @Value("${api.googledirectionskey}")
    private String mapsApiKey;

    private GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(mapsApiKey)
        .build();
        

    public DirectionsApiRequest getDirections (String directionFrom, String directionTo){
        try {
            return DirectionsApi.getDirections(context, directionFrom, directionTo);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;            
        }
    }
}
