package com.team5.deliveryApi.services;

import com.team5.deliveryApi.models.GroceryItem;
import com.team5.deliveryApi.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class GroceryServiceTest {

    @Autowired
    private GroceryService groceryService;

    @MockBean
    private GroceryItemRepository groceryItemRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void shouldReturnAllGroceryItem(){
        GroceryItem groceryItem = new GroceryItem(1,"Test Item",new BigDecimal(100));
        Mockito.when(groceryItemRepository.findById(Mockito.any())).thenReturn(Optional.of(groceryItem));
        Assertions.assertNotNull(groceryService.getGroceries());
    }

    //Not Passing
    @Test
    public void shouldAddGrocery(){
        GroceryItem groceryItem = new GroceryItem(1,"Test Item",new BigDecimal(100));
        Mockito.when(groceryItemRepository.findById(Mockito.any())).thenReturn(Optional.of(groceryItem));
        GroceryItem groceryItem2 = new GroceryItem(2,"Test Item",new BigDecimal(100));
        groceryService.addGrocery(groceryItem);
        groceryService.addGrocery(groceryItem2);
        System.out.println(groceryService.getGroceries());
        Assertions.assertEquals(groceryService.getGroceries().size(),2);
    }
}
