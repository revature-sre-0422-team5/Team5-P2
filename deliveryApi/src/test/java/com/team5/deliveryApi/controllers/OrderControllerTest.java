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
                "2049 London Street", "", "Walmart",
                new Customer(), new ArrayList<>(),null);

        Mockito.when(orderService.findByOrderId(2))
               .thenReturn(order);
        mockMvc.perform(MockMvcRequestBuilders.get("/order/viewId/2"))
                .andExpect(status().isOk());*/
    }

    /**
     * Tests if create() returns a
     * Bad response if user is not created.
     * @throws Exception
     */
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

    /**
     * Tests if viewStatusById() returns an
     * ok response if users are found.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnViewStatusById() throws Exception {
        if(orderService != null){
            Mockito.when(orderService.viewStatusById(Mockito.anyInt()))
                    .thenReturn(OrderStatus.MakingOrder);
            mockMvc.perform(MockMvcRequestBuilders.get("/order/status/1"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if viewAllOrder() returns an
     * ok response if orders are found.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnViewAllOrders() throws Exception {
        if (orderService != null) {
            Mockito.when(orderService.viewAllOrders())
                    .thenReturn(new ArrayList<>());
            mockMvc.perform(MockMvcRequestBuilders.get("/order/all"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if PayById() returns an
     * ok response if pay status is changed.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnPayById() throws  Exception{
        if (orderService != null) {
            Mockito.when(orderService.payOrder(Mockito.anyInt()))
                    .thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.put("/order/pay/1"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if FindOrderById() returns an
     * ok response if order is found.
     * @throws Exception
     */
    @Test
    public void shouldReturnOKOnFindOrderById() throws Exception{
        if (orderService != null) {
            Mockito.when(orderService.findByOrderId(Mockito.anyInt()))
                    .thenReturn(new Order());
            mockMvc.perform(MockMvcRequestBuilders.get("/order/viewId/1"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if UpdateDescription() returns an
     * ok response if description is updated.
     * @throws Exception
     */
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

    /**
     * Tests if RemoveItem() returns an
     * ok response if item is removed.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnRemoveItem() throws Exception{
        if (orderService != null) {
            Mockito.when(orderService.removeItem(Mockito.any(),Mockito.anyInt()))
                    .thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.delete("/order/removeitem/1/1"))
                    .andExpect(status().isOk());
        }
    }
    /**
     * Tests if AddItem() returns an
     * ok response if item is added.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnAddItem() throws Exception{
        if (orderService != null) {
            Mockito.when(orderService.addItem(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt()))
                    .thenReturn(new Order());
            mockMvc.perform(MockMvcRequestBuilders.post("/order/addItem/1/1?qty=1"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if sumbitOrder() returns an
     * ok response if order is submitted.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnSubmitOrder() throws Exception {
        if (orderService != null) {
            Mockito.when(orderService.submitOrder(Mockito.anyInt()))
                    .thenReturn(Mockito.anyString());
            mockMvc.perform(MockMvcRequestBuilders.put("/order/submit/1"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if changeOrderStatus() returns an
     * ok response if status is changed.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnChangeOrderStatus() throws Exception{
        if (orderService != null) {
            Mockito.when(orderService.updateOrderStatus(Mockito.anyInt(),Mockito.any()))
                    .thenReturn(new Order());
            mockMvc.perform(MockMvcRequestBuilders.put("/order/status/1?status=MakingOrder"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if AssignShopper() returns an
     * ok response if shopper is assigned.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnAssignShopper() throws Exception{
        if (orderService != null) {
            Mockito.when(orderService.assignShopper(Mockito.anyInt(),Mockito.anyInt()))
                    .thenReturn(new Order());
            mockMvc.perform(MockMvcRequestBuilders.put("/order/assign/1/2"))
                    .andExpect(status().isOk());
        }
    }

    /**
     * Tests if cancelOrder() returns an
     * ok response if order is cancelled.
     * @throws Exception
     */
    @Test
    public void shouldReturnOkOnCancelOrder() throws Exception {
        if (orderService != null) {
            Mockito.when(orderService.deleteOrder(Mockito.any()))
                    .thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/1"))
                    .andExpect(status().isOk());
        }
    }

    /*
    @Test
    public void shouldReturnOkOnGetCustomerDirection() throws Exception {
        if (orderService != null) {
            Mockito.when(orderService.findByOrderId(Mockito.anyInt()))
                    .thenReturn(new Order());
            mockMvc.perform(MockMvcRequestBuilders.get("/order/directions/1"))
                    .andExpect(status().isOk());
        }
    }*/
}

