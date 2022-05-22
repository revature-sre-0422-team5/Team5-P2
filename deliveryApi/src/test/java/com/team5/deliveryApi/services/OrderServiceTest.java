package com.team5.deliveryApi.services;

import com.team5.deliveryApi.dto.OrderLocation;
import com.team5.deliveryApi.dto.OrderStatus;
import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.models.UserNotFoundException;
import com.team5.deliveryApi.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ShopperRepository shopperRepository;

    @MockBean
    private GroceryItemRepository groceryItemRepository;
    @Mock
    private RestTemplate restTemplate;

    protected List<Order> list = new ArrayList<Order>();

    /**
     * Tests if the order service assigns a shopper to an order properly.
     */
   @Test
    public void shouldAssignShopperToOrder() {
    Optional<Shopper> shopper = Optional.of(new Shopper(1, "john_smith", "passwordJohn",
                "John Smith", "john.smith@gmail.com", true, new ArrayList<>()));
        Optional<Order> order = Optional.of(new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new Customer(),new ArrayList<>(), null));
        Mockito.when(orderRepository.findById(Mockito.anyInt())).thenReturn(order);
        Mockito.when(shopperRepository.findById(Mockito.anyInt())).thenReturn(shopper);

        Order returned = orderService.assignShopper(1, 1);
        Assertions.assertEquals(returned.getShopper(), shopper.get());

    }


    /**
     * Tests if the order status gets updated properly.
     */
    @Test
    public void shouldUpdateOrderStatus() {

    }

    /**
     * Tests the email notification sender.
     */
    @Test
    public void shouldTestNotificationSend() {
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
                Mockito.any(), Mockito.anyMap())).thenReturn(ResponseEntity.ok().build());
        Assertions.assertThrows(Exception.class, () -> orderService.sendNotification("test@gmail.com",
                "test subject", "test message"));
    }

    @Test
    public void shouldSaveOrderWork(){
       Order order = new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new Customer(), new ArrayList<>(), null);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        Customer customer = new Customer(1, "John Smith", "johnsmithy123",
                "JohnSmithPassword", "100 Nowhereville",
                false, "john.smith@gmail.com", (ArrayList<Order>) list);
        Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));
        orderService.saveOrder(1,order);
        Assertions.assertNotNull(orderService.viewAllOrders());
    }
    @Test
    public void shouldAddItemById(){
       /* Order order = new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new Customer(), new ArrayList<>(), null);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        orderService.saveOrder(1,order);
        Assertions.assertNotNull(orderService.addItem(1,3,5))*/
    }
    @Test
    public void shouldUpdateOrderLocation(){
        Order order = new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new Customer(), new ArrayList<>(), null);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        orderService.saveOrder(1,order);
        OrderLocation orderLocation = new OrderLocation("Toronto");
        orderService.updateDescription(order,orderLocation);
        Assertions.assertEquals("Toronto",orderService.findByOrderId(1).getDescription());
    }
    @Test
    public void shouldFindOrderByID(){
        Order order = new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new Customer(), new ArrayList<>(), null);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        orderService.saveOrder(1,order);
        Assertions.assertEquals("11/11/1111",orderService.findByOrderId(1).getDate());
    }
    @Test
    public void shouldChangePayStatus(){
      /*  Order order = new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new Customer(), new ArrayList<>(), null);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        orderService.saveOrder(1,order);
        Assertions.assertTrue( orderService.payOrder(1));
        Assertions.assertEquals("Paid",orderService.findByOrderId(1).getPay_status());*/
    }

    @Test
    public void ShouldDeleteOrder() {
    /* Order order = new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new Customer(), new ArrayList<>(), null);
    Mockito.when(orderService.deleteOrder(order)).thenReturn(true);
    orderService.saveOrder(1,order);
    Assertions.assertNotEquals("11/11/1111",orderService.findByOrderId(1).getDate());*/


    }

}




