package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.dto.ItemStatus;
import com.team5.deliveryApi.models.GroceryItem;
import com.team5.deliveryApi.models.Item;
import com.team5.deliveryApi.models.ItemNotFoundException;
import com.team5.deliveryApi.services.ItemService;
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
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    /**
     * Tests if setItemStatus() returns an ok
     * response if the item status was set.
     */
    @Test
    public void shouldReturnOkResponseSetItemStatus() throws Exception {
        Mockito.when(itemService.setItemStatus(Mockito.anyInt(), Mockito.anyInt(), Mockito.any()))
                .thenReturn(new Item(1, ItemStatus.Added, new GroceryItem()));
        mockMvc.perform(MockMvcRequestBuilders.put("/item/status/1/1/2?status=Added"))
                .andExpect(status().isOk());
        Mockito.when(itemService.replaceItem(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(new Item(1, ItemStatus.Added, new GroceryItem()));
        mockMvc.perform(MockMvcRequestBuilders.put("/item/status/1/1/2?status=Replaced"))
                .andExpect(status().isOk());
    }

    /**
     * Tests if setItemStatus() returns
     * a bad request response if an exception is thrown.
     */
    @Test
    public void shouldReturnBadRequest() throws Exception {
        Mockito.when(itemService.setItemStatus(Mockito.anyInt(), Mockito.anyInt(), Mockito.any()))
                .thenThrow(new ItemNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.put("/item/status/1/1/2?status=Added"))
                .andExpect(status().isBadRequest());
    }
}
