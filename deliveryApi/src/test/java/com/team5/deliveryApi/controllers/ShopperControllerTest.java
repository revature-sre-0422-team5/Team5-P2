package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.services.ShopperService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopperControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShopperService shopperService;

    /**
     * Tests if viewAllShopper() returns an
     * ok response if shoppers are found.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnViewAllShoppers() throws Exception {
        if(shopperService != null){
            Mockito.when(shopperService.viewShopper()).thenReturn(new ArrayList<>());
            mockMvc.perform(MockMvcRequestBuilders.get("/shopper/all"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if viewAllShopperById() returns an
     * ok response if shopper is found.
     * @throws Exception
     */
    @Test
    public void  shouldReturnOkOnViewShopperById() throws Exception {
        if(shopperService != null){
            Mockito.when(shopperService.viewShopperById(Mockito.anyInt()))
                    .thenReturn(new Shopper(1,"username","password","name","email",false, new ArrayList<>()));
            mockMvc.perform(MockMvcRequestBuilders.get("/shopper/1"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if createShopperAccount() returns an
     * ok response if shopper is created.
     * @throws Exception
     */
    @Test
    public void shouldCreateShopperAccount() throws Exception {
        if(shopperService != null) {
            Mockito.when(shopperService.saveShopper(new Shopper())).thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.post("/shopper/create")
                    .contentType("application/json")
                    .content("\t{\n" +
                            "\t\t\"name\": \"newCustomer\",\n" +
                            "\t\t\"username\": \"login\",\n" +
                            "\t\t\"password\": \"password\",\n" +
                            "\t\t\"email\": \"email\",\n" +
                            "\t\t\"loggedIn\": false,\n" +
                            "\t\t\"orders\": []\n" +
                            "\t}")
            ).andExpect(status().isOk());
        }
    }

    /**
     * Tests if onLogin() returns an
     * ok response if shopper is logged In.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnLogin() throws Exception {
        if(shopperService != null){
            Mockito.when(shopperService.saveShopper(new Shopper())).thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.post("/shopper/login")
                    .contentType("application/json")
                    .content("\t{\n" +
                            "\t\t\"name\": \"newCustomer\",\n" +
                            "\t\t\"username\": \"login\",\n" +
                            "\t\t\"password\": \"password\",\n" +
                            "\t\t\"email\": \"email\",\n" +
                            "\t\t\"loggedIn\": false,\n" +
                            "\t\t\"orders\": []\n" +
                            "\t}"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if logOut() returns an
     * ok response if shopper is loggedOut.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnLogout() throws Exception {
        if(shopperService != null){
            Mockito.when(shopperService.saveShopper(new Shopper())).thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.post("/shopper/logout")
                            .contentType("application/json")
                            .content("\t{\n" +
                                    "\t\t\"name\": \"newCustomer\",\n" +
                                    "\t\t\"username\": \"login\",\n" +
                                    "\t\t\"password\": \"password\",\n" +
                                    "\t\t\"email\": \"email\",\n" +
                                    "\t\t\"loggedIn\": false,\n" +
                                    "\t\t\"orders\": []\n" +
                                    "\t}"))
                    .andExpect(status().isOk());
        }
    }
}
