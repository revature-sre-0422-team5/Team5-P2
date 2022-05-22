package com.team5.deliveryApi.controllers;


import com.team5.deliveryApi.dto.OrderStatus;
import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    @Test
    public void shouldReturnOrder() throws Exception{
     /*  Order order=new Order(2, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "Walmart",
                new Customer(), new ArrayList<>(),null);

        Mockito.when(orderService.findByOrderId(2))
               .thenReturn(order);
        mockMvc.perform(MockMvcRequestBuilders.get("/order/viewId/2"))
                .andExpect(status().isOk());*/



    }


    }

