package com.team5.deliveryApi.dtos;

import com.team5.deliveryApi.dto.OrderItemAdd;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DTOsTest {

    @Test
    public void shouldGetterForOrderItemAddWork(){
        OrderItemAdd orderItemAdd = new OrderItemAdd("itemName","description",1000);
        Assertions.assertEquals("itemName",orderItemAdd.getItemName());
        Assertions.assertEquals("description",orderItemAdd.getItemDescription());
    }
}
