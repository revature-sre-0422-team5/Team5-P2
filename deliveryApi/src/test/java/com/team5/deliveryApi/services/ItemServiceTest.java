package com.team5.deliveryApi.services;

import com.team5.deliveryApi.dto.ItemStatus;
import com.team5.deliveryApi.dto.OrderStatus;
import com.team5.deliveryApi.models.*;
import com.team5.deliveryApi.repositories.*;
import com.team5.deliveryApi.models.GroceryItem;
import com.team5.deliveryApi.models.Item;
import com.team5.deliveryApi.models.ItemNotFoundException;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.repositories.GroceryItemRepository;
import com.team5.deliveryApi.repositories.ItemRepository;
import com.team5.deliveryApi.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

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

    @Test
    public void shouldChangeItemStatus() throws ItemNotFoundException {
        Order order = new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new Customer(), new ArrayList<>(), null);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        GroceryItem groceryItem = new GroceryItem(1,"Test Item",new BigDecimal(100));
        Mockito.when(groceryItemRepository.findById(Mockito.any())).thenReturn(Optional.of(groceryItem));
        orderService.addItem(1,1,1);
        Item item = new Item(1,1, ItemStatus.Added,groceryItem);
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.of(item));
        itemService.setItemStatus(1,1,ItemStatus.Unavailable);
        Assertions.assertEquals(ItemStatus.Unavailable, itemService.findItemById(1).getStatus());

        
    }

    @Test
    public void shouldThrowItemNotFoundExceptionOnChangeItemStatus() throws ItemNotFoundException {
        ItemNotFoundException ex = Assertions.assertThrows(ItemNotFoundException.class, () -> {
            itemService.setItemStatus(1,1,ItemStatus.Unavailable);
        });
    }

    @Test
    public void shouldReplaceItem() throws ItemNotFoundException {
        Order order = new Order(1, "11/11/1111", OrderStatus.MakingOrder, "2049 London Street", "", "My grocery items", new Customer(), new ArrayList<>(), null);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        GroceryItem groceryItem = new GroceryItem(1,"Test Item",new BigDecimal(100));
        Mockito.when(groceryItemRepository.findById(Mockito.any())).thenReturn(Optional.of(groceryItem));
        Item item = new Item(1,1, ItemStatus.Added,groceryItem);
        Item item2 = new Item(2,1, ItemStatus.Replaced,groceryItem);
        orderService.addItem(1,1,1);
        orderService.addItem(1,2,1);
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.of(item));

        Assertions.assertEquals(item2, itemService.replaceItem(1,1,2));
    }

    @Test
    public void shouldThrowItemNotFoundExceptionOnReplaceItem() throws ItemNotFoundException {
        ItemNotFoundException ex = Assertions.assertThrows(ItemNotFoundException.class, () -> {
            itemService.replaceItem(1,1,2);
        });
    }

    /**
     * Tests if the item status is set properly.
     */
    @Test
    public void shouldChangeItemStatus2() throws ItemNotFoundException {
        Order order = new Order();
        order.setOrderId(1);
        Item item = new Item();
        item.setId(1);
        order.setItems(Collections.singletonList(item));
        Mockito.when(orderRepository.getById(Mockito.anyInt())).thenReturn(order);
        Mockito.when(itemRepository.save(Mockito.any())).thenReturn(item);
        Assertions.assertEquals(ItemStatus.Added,
                itemService.setItemStatus(1, 1, ItemStatus.Added).getStatus());
    }

    /**
     * Tests if the grocery item is changed properly.
     */
    @Test
    public void shouldReplaceGroceryItem() throws ItemNotFoundException {
        Order order = new Order();
        order.setOrderId(1);
        Item item = new Item();
        item.setId(1);
        GroceryItem groceryItem = new GroceryItem();
        order.setItems(Collections.singletonList(item));
        Mockito.when(orderRepository.getById(Mockito.anyInt())).thenReturn(order);
        Mockito.when(groceryItemRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(groceryItem));
        Mockito.when(itemRepository.save(Mockito.any())).thenReturn(item);
        Assertions.assertEquals(groceryItem, itemService.replaceItem(1, 1, 2).getGroceryItem());
    }

    /**
     * Tests if calling any item service method will
     * throw an exception if an item is not found.
     */
    @Test
    public void shouldThrowItemNotFoundException() {
        Order order = new Order();
        order.setOrderId(1);
        Item item = new Item();
        item.setId(1);
        order.setItems(Collections.singletonList(item));
        Mockito.when(orderRepository.getById(Mockito.anyInt())).thenReturn(order);
        Mockito.when(groceryItemRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ItemNotFoundException.class, () -> {
            itemService.setItemStatus(1, 2, ItemStatus.Added);
        });
        Assertions.assertThrows(ItemNotFoundException.class, () -> {
            itemService.replaceItem(1, 2, 1);
        });
        Assertions.assertThrows(ItemNotFoundException.class, () -> {
            itemService.replaceItem(1, 1, 2);
        });
    }
}
