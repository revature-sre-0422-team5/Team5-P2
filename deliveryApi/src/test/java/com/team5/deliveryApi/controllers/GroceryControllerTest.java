package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.services.GroceryService;
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
public class GroceryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroceryService groceryService;

    /**
     * Tests if GetGroceries() returns an
     * ok response if groceries are found.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnGetGroceries() throws Exception {
        if(groceryService != null){
            Mockito.when(groceryService.getGroceries()).thenReturn(new ArrayList<>());
            mockMvc.perform(MockMvcRequestBuilders.get("/grocery/get-all"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if addGrocery() returns an
     * ok response if grocery is created.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnAddGrocery() throws Exception {
        if(groceryService != null){
            Mockito.when(groceryService.getGroceries()).thenReturn(new ArrayList<>());
            mockMvc.perform(MockMvcRequestBuilders.post("/grocery/add-grocery-item")
                    .contentType("application/json")
                    .content("\t{\n" +
                            "\t\t\"productName\": \"productName\",\n" +
                            "\t\t\"cost\": 23\n" +
                            "\t}")
                    )
                    .andExpect(status().isOk());
        }
    }
}
