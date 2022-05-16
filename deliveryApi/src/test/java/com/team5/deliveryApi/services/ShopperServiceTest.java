package com.team5.deliveryApi.services;

import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.models.UserNotFoundException;
import com.team5.deliveryApi.repositories.ShopperRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

@SpringBootTest(classes = ShopperService.class)
public class ShopperServiceTest {
    @Autowired
    private ShopperService shopperService;
    @MockBean
    private ShopperRepository shopperRepository;

    @Test
    public void verifyShopperDetails() throws UserNotFoundException{
        Shopper shopper = new Shopper(1,"user1","P@$$w0rd","John", "John@outlook.com",false,new ArrayList<>());
        Shopper updated = shopperRepository.save(shopper);
        Assertions.assertEquals(1,shopper.getId());
        Assertions.assertEquals("user1",shopper.getUsername());
        Assertions.assertEquals("P@$$w0rd",shopper.getPassword());
        Assertions.assertEquals("John",shopper.getName());
        Assertions.assertEquals("John@outlook.com",shopper.getEmail());
        Assertions.assertEquals(false,shopper.isLoggedIn());
    }
}
