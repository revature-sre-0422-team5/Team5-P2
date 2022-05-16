package com.team5.deliveryApi.services;

import com.team5.deliveryApi.dto.OrderLocation;
import com.team5.deliveryApi.models.Item;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void shouldSaveOrderWork(){
        Order order = new Order(1,1,"testDate","pending","Toronto","Test Address","unpaid","From location","Test description",new ArrayList<Item>());
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        orderService.saveOrder(order);
        Assertions.assertNotNull(orderService.viewAllOrders());
    }

    /**
     * Test if OrderService updates pay status
     */
    @Test
    public void shouldChangePayStatus(){
        Order order = new Order(1,1,"testDate","pending","Toronto","Test Address","unpaid","From location","Test description",new ArrayList<Item>());
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        orderService.saveOrder(order);
        //orderService.payOrder(1);
        Assertions.assertTrue( orderService.payOrder(1));
        Assertions.assertEquals("Paid",orderService.viewStatusById(1));
    }

    @Test
    public void shouldFindOrderByID(){
        Order order = new Order(1,1,"testDate","pending","Toronto","Test Address","unpaid","From location","Test description",new ArrayList<Item>());
        orderService.saveOrder(order);
        Assertions.assertEquals("testDate",orderService.findByOrderId(1).getDate());
    }

    @Test
    public void shouldUpdateLocation(){
        Order order = new Order(1,1,"testDate","pending","Toronto","Test Address","unpaid","From location","Test description",new ArrayList<Item>());
        orderService.saveOrder(order);
        OrderLocation orderLocation = new OrderLocation("Tononto","House");
        orderService.updateLocation(order,orderLocation);
        Assertions.assertEquals("Toronto",orderService.findByOrderId(1).getFrom_location());
    }




}
