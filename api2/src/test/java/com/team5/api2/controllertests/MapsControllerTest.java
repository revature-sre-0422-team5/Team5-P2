package com.team5.api2.controllertests;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.DirectionsResult;
import com.team5.api2.services.MapsServices;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Maps Controller tests
 */
@SpringBootTest
@AutoConfigureMockMvc
class MapsControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    
	@MockBean
    private MapsServices mapsServices;

	@Autowired
    private ObjectMapper mapper;

	/**
	 * Test how controller responds to null and regular inputs for distance calculation
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test
	void controllerResponseToDistanceRequest() throws JsonProcessingException, Exception {
		Map<String, String> request = new HashMap<>();

		String cnTower = "290 Bremner Blvd, Toronto, ON M5V 3L9";
		request.put("locationFrom", cnTower);

		mockMvc.perform(
			MockMvcRequestBuilders.get("/getDistance")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
			).andExpect(status().isBadRequest());

		String artGalleryOntario = "317 Dundas St W, Toronto, ON M5T 1G4";

		request.put("locationTo", artGalleryOntario);

		Mockito.when(mapsServices.getDistance(cnTower, artGalleryOntario)).thenReturn(1234L);

		mockMvc.perform(
			MockMvcRequestBuilders.get("/getDistance")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
			).andExpect(status().isOk());

		Mockito.verify(mapsServices, times(1)).getDistance(cnTower, artGalleryOntario);

		//Check responses when unchecked error
		Mockito.when(mapsServices.getDistance(cnTower, artGalleryOntario)).thenThrow(IllegalStateException.class);

		mockMvc.perform(
			MockMvcRequestBuilders.get("/getDistance")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
			)
		.andExpect(status().isInternalServerError());
	}

	/**
	 * Test how controller responds to null and regular inputs for getting directions
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test
	void controllerResponseToDirectionsRequest () throws JsonProcessingException, Exception{
		Map<String, String> request = new HashMap<>();

		String cnTower = "290 Bremner Blvd, Toronto, ON M5V 3L9";
		request.put("locationFrom", cnTower);

		mockMvc.perform(
			MockMvcRequestBuilders.get("/directions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
			).andExpect(status().isBadRequest());

		String artGalleryOntario = "317 Dundas St W, Toronto, ON M5T 1G4";

		request.put("locationTo", artGalleryOntario);

		Mockito.when(mapsServices.getDirections(cnTower, artGalleryOntario)).thenReturn(new DirectionsResult());

		mockMvc.perform(
			MockMvcRequestBuilders.get("/directions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
			).andExpect(status().isOk());

		Mockito.verify(mapsServices, times(1)).getDirections(cnTower, artGalleryOntario);

		//Check responses when unchecked error
		Mockito.when(mapsServices.getDirections(cnTower, artGalleryOntario)).thenThrow(IllegalStateException.class);
		
		mockMvc.perform(
			MockMvcRequestBuilders.get("/directions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
			)
		.andExpect(status().isInternalServerError());
	}

}
