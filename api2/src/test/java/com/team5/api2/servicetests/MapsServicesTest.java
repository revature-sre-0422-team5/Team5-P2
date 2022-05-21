package com.team5.api2.servicetests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.io.IOException;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.team5.api2.services.MapsServices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Maps Services Test
*/
@SpringBootTest
class MapsServicesTest {

    @Mock
    private GeoApiContext geoApiCtx;

    @InjectMocks
    private MapsServices mapsService;

    private String cnTower = "290 Bremner Blvd, Toronto, ON M5V 3L9";
    private String ago = "317 Dundas St W, Toronto, ON M5T 1G4";

    /**
     * Check that static distance api methods gets called and service returns Long.MAX_VALUE for the method when api has errors
     */
    @Test
    void checkDistanceApiGetsCalled (){        

        //Mock static requirements
        try (MockedStatic<DistanceMatrixApi> distanceApiMock = Mockito.mockStatic(DistanceMatrixApi.class)){
            distanceApiMock.when( () -> DistanceMatrixApi.getDistanceMatrix(any(), any(), any()).await())
            .thenCallRealMethod();

            mapsService.getDistance(cnTower, ago);

            distanceApiMock.verify(
                () -> DistanceMatrixApi.getDistanceMatrix(any(), any(), any()), 
                times(1)
            );

            //Check null returned when api has errors
            distanceApiMock.when( () -> DistanceMatrixApi.getDistanceMatrix(any(), any(), any()))
            .thenThrow(IllegalStateException.class);

            Assertions.assertEquals(Long.MAX_VALUE, mapsService.getDistance(cnTower, ago));
        }
    }

    /**
     * Check that static directions api method gets called and service returns null when api has errors
     * @throws IOException
     * @throws InterruptedException
     * @throws ApiException
     */
    @Test
    void checkDirectionsApiGetsCalled() throws ApiException, InterruptedException, IOException{
        //Mock static requirements
        try (MockedStatic<DirectionsApi> directionApiMock = Mockito.mockStatic(DirectionsApi.class)){
            
            directionApiMock.when( () -> DirectionsApi.getDirections(any(), anyString(), anyString()).await())
            .thenCallRealMethod();

            mapsService.getDirections(cnTower, ago);

            directionApiMock.verify(
                () -> DirectionsApi.getDirections(any(), anyString(), anyString()), 
                times(1)
            );

        //Check null returned when api has errors
        directionApiMock.when( () -> DirectionsApi.getDirections(any(), anyString(), anyString()))
            .thenThrow(IllegalStateException.class);

            Assertions.assertEquals(null, mapsService.getDirections(cnTower, ago));
        }
    }

}
