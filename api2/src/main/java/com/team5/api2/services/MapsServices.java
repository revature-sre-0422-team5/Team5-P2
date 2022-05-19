package com.team5.api2.services;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//Service just wraps Google Maps APIs
@Service("Maps")
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
            return DirectionsApi.getDirections(context, directionFrom, directionTo).await();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getDistance (String locationFrom, String locationTo){
        try {
            return DistanceMatrixApi.getDistanceMatrix(context, new String[]{locationFrom},new String[]{locationTo}).await()
            .rows[0].elements[0]
            .distance.inMeters;
        }
        catch (Exception e){
            e.printStackTrace();
            return Long.MAX_VALUE;
        }
    }

}
