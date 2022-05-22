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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    @Test
    public void shouldReturnOrder() throws Exception{
     /*  Order order=new Order(2, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "unpaid", "Walmart",
                new Customer(), new ArrayList<>(),null);

        Mockito.when(orderService.findByOrderId(2))
               .thenReturn(order);
        mockMvc.perform(MockMvcRequestBuilders.get("/order/viewId/2"))
                .andExpect(status().isOk());*/
    }

    @Test
    public void shouldReturnBadOnCreateOrder() throws Exception {
        if(orderService != null) {
            Mockito.when(orderService.saveOrder(Mockito.anyInt(),Mockito.any())).thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.post("/order/create/1")
                    .contentType("application/json")
                    .content("\t\t{\n" +
                            "\t\t\"pay_status\": \"unpaid\",\n" +
                            "\t\t\"date\": \"date\",\n" +
                            "\t\t\"store_location\": \"Toronto\",\n" +
                            "\t\t\"location_description\": \"House\"\n" +
                            "\t}")
            ).andExpect(status().isBadRequest());
        }
    }

    @Test
    public void shouldReturnOkOnViewStatusById() throws Exception {
        if(orderService != null){
            Mockito.when(orderService.viewStatusById(Mockito.anyInt()))
                    .thenReturn(OrderStatus.MakingOrder);
            mockMvc.perform(MockMvcRequestBuilders.get("/order/status/1"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void shouldReturnOkOnViewAllOrders() throws Exception {
        if (orderService != null) {
            Mockito.when(orderService.viewAllOrders())
                    .thenReturn(new ArrayList<>());
            mockMvc.perform(MockMvcRequestBuilders.get("/order/all"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void shouldReturnOkOnPayById() throws  Exception{
        if (orderService != null) {
            Mockito.when(orderService.payOrder(Mockito.anyInt()))
                    .thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.put("/order/pay/1"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void shouldReturnOKOnFindOrderById() throws Exception{
        if (orderService != null) {
            Mockito.when(orderService.findByOrderId(Mockito.anyInt()))
                    .thenReturn(new Order());
            mockMvc.perform(MockMvcRequestBuilders.get("/order/viewId/1"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void shouldReturnOkOnUpdateDescription() throws Exception {
        if (orderService != null) {
            Mockito.when(orderService.updateDescription(Mockito.any(),Mockito.any()))
                    .thenReturn(new Order());
            mockMvc.perform(MockMvcRequestBuilders.put("/order/adddescription/1")
                            .contentType("application/json")
                            .content("\t{\n" +
                                    "\t\t\"dto_description\": \"new Location\"\n" +
                                    "\t}"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void shouldReturnOkOnRemoveItem() throws Exception{
        if (orderService != null) {
            Mockito.when(orderService.removeItem(Mockito.any(),Mockito.anyInt()))
                    .thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.delete("/order/removeitem/1/1"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void shouldReturnOkOnAddItem() throws Exception{
        if (orderService != null) {
            Mockito.when(orderService.addItem(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt()))
                    .thenReturn(new Order());
            mockMvc.perform(MockMvcRequestBuilders.post("/order/addItem/1/1?qty=1"))
                    .andExpect(status().isOk());
        }
    }








}

