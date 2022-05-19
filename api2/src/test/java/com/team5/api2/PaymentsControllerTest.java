package com.team5.api2;

import java.util.HashMap;
import java.util.Map;

import com.team5.api2.services.PaymentsServices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @Test
    void getOrderCost () throws Exception{
        Map<String, String> request = new HashMap<>();

        request.put("", "");

        mockMvc.perform(
            MockMvcRequestBuilders.get("/get-order-cost")
        ).andExpectAll(status().isOk());
    }
    
}
