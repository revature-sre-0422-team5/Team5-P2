package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    /**
     * Tests if subscribeEmailById() returns an
     * ok response if user is found.
     */
    @Test
    public void shouldReturnOkOnEmailSubscription() throws Exception {
        Mockito.when(customerService.updateEmailSubscription(Mockito.anyInt(), Mockito.anyBoolean()))
                .thenReturn(new Customer());
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/subscribe/1?status=true"))
                .andExpect(status().isOk());
    }
}
