package com.team5.deliveryApi.controllers;


import com.team5.deliveryApi.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    @Test
    public void shouldReturnOrder() throws Exception{

   /*Mockito.when(orderService.findByOrderId(Mockito.anyInt()))
               .thenReturn(
     new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new Customer(), new ArrayList<>(), null));
        mockMvc.perform(MockMvcRequestBuilders.get("/order/viewId/1"))
                .andExpect(status().isOk());*/
    }


    }

