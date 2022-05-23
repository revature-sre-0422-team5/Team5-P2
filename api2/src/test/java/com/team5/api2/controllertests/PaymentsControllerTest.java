package com.team5.api2.controllertests;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.api2.services.PaymentsServices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

        Mockito.when(paymentsService.chargeUser(1, 1234)).thenThrow(IllegalStateException.class);

        mockMvc.perform (
            MockMvcRequestBuilders.post("/checkout-order")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
        ).andExpectAll(status().isInternalServerError());
    }

    @Test
    void paymentRedirect () throws Exception {
        Mockito.when(paymentsService.processOrderStatus("test-string")).thenReturn("true");

        Map<String, String> request = new HashMap<>();
        MvcResult body = mockMvc.perform (
            MockMvcRequestBuilders.get("/success?session_id=test-string")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
        ).andExpectAll(status().isOk()).andReturn();

        Assertions.assertEquals("true",body.getResponse().getContentAsString());

        Mockito.when(paymentsService.processOrderStatus("test-string")).thenThrow(IllegalStateException.class);

        mockMvc.perform (
            MockMvcRequestBuilders.get("/success?session_id=test-string")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
        ).andExpectAll(status().isInternalServerError());
    }
    
}
