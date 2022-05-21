package com.team5.api2.controllertests;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.api2.services.PaymentsServices;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Payments Controller tests
 */
@SpringBootTest
@AutoConfigureMockMvc
class PaymentsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PaymentsServices paymentsService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getOrderCost () throws Exception{
        Map<String, String> request = new HashMap<>();

        request.put("orderIdReference", "1");

        mockMvc.perform(
            MockMvcRequestBuilders.get("/get-order-cost")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
        ).andExpectAll(status().isOk());
    }

    @Test
    void checkoutOrder () throws Exception{
        Map<String, String> request = new HashMap<>();

        request.put("orderReferenceId", "1");
        request.put("orderAmount", "1234");
        request.put("receiptEmail", "test@example.com");

        Mockito.when(paymentsService.chargeUser(1,1234)).thenReturn("http://example.com");

        mockMvc.perform (
            MockMvcRequestBuilders.post("/checkout-order")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
        ).andExpectAll(status().isOk());
    }

    @Test
    void payShopper() throws Exception{
        mockMvc.perform (
            MockMvcRequestBuilders.post("/pay-shopper")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(status().isInternalServerError());
    }
    
}
