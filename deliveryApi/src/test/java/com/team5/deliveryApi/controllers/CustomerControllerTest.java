package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    @Test
    public void shouldLogOut() throws Exception {

        Mockito.when(customerService.logout(any())).thenReturn(true);
        mockMvc.perform(get("/customer/logout")
                        .contentType("application/json")
                        .content("{\"username\": \"rosh\", \"password\": \"rosh\"}"))
                .andExpect(status().isOk()).andExpect(content().string("User logged out"));

    }
    @Test
    public void shouldLogIn() throws Exception {

        Mockito.when(customerService.login(any())).thenReturn(true);
        mockMvc.perform(get("/customer/login")
                        .contentType("application/json")
                        .content("{\"username\": \"rosh\", \"password\": \"rosh\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotLogIn() throws Exception {

        Mockito.when(customerService.login(any())).thenReturn(false);
        mockMvc.perform(get("/customer/login")
                        .contentType("application/json")
                        .content("{\"username\": \" \", \"password\": \"rosh\"}"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void shouldCreateCustomer() throws Exception {
  /*    Customer mockCustomer = new Customer();
        mockCustomer.setEmail("rosh@gmail.com");
        mockCustomer.setPassword("rosh");
        mockCustomer.setLocation("Toronto");
        mockCustomer.setUsername("rosh");
        mockCustomer.setName("rosh");



       Mockito.when(customerService.saveCustomer(any())).thenReturn(true);

       mockMvc.perform(MockMvcRequestBuilders.post("/customer/new")
                        .contentType("application/json")
                .content("{\"name\": \"rosh\",\"username\": \"rosh\",\"password\": \"rosh\",\"email\": \"rosh@gmail.com\",\"location\": \"Toronto\"}"))
               .andExpect(content().string("Account created successfully"));*/
    }
}
