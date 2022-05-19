package com.team5.deliveryApi.services;

import com.team5.deliveryApi.dto.OrderStatus;
import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.repositories.CustomerRepository;
import com.team5.deliveryApi.repositories.GroceryItemRepository;
import com.team5.deliveryApi.repositories.OrderRepository;
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
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ShopperRepository shopperRepository;

    @MockBean
    private GroceryItemRepository groceryItemRepository;

    /**
     * Tests if the order service assigns a shopper to an order properly.
     */
    @Test
    public void shouldAssignShopperToOrder() {
        Optional<Shopper> shopper = Optional.of(new Shopper(1, "john_smith", "passwordJohn",
                "John Smith", "john.smith@gmail.com", true, new ArrayList<>()));
        Optional<Order> order = Optional.of(new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new ArrayList<>(), new Customer(), null));
        Mockito.when(orderRepository.findById(Mockito.anyInt())).thenReturn(order);
        Mockito.when(shopperRepository.findById(Mockito.anyInt())).thenReturn(shopper);

        Order returned = orderService.assignShopper(1, 1);
        Assertions.assertEquals(returned.getShopper(), shopper.get());
    }
}
