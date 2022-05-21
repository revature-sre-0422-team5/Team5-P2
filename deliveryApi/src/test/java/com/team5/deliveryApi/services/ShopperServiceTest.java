package com.team5.deliveryApi.services;

import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.repositories.ShopperRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class ShopperServiceTest {

    @Autowired
    private ShopperService shopperService;


    @MockBean
    private ShopperRepository shopperRepository;

    @Test
    public void shouldSaveShopper(){
        Shopper shopper = new Shopper(1,"username","password","name","email",false,new ArrayList<>());
        Mockito.when(shopperRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(shopper));
        shopperService.saveShopper(shopper);
        Assertions.assertNotNull(shopperService.viewShopper());
    }

    @Test
    public void shouldViewShopperById(){
        Shopper shopper = new Shopper(1,"username","password","name","email",false,new ArrayList<>());
        Mockito.when(shopperRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(shopper));
        shopperService.saveShopper(shopper);
        Assertions.assertEquals("username",shopperService.getShopperById(1).get().getUsername());
    }
}
