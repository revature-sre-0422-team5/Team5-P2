package com.team5.deliveryApi.services;

import com.team5.deliveryApi.dto.ItemStatus;
import com.team5.deliveryApi.dto.OrderStatus;
import com.team5.deliveryApi.models.*;
import com.team5.deliveryApi.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
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

}
