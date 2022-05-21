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

    //Null Point Exception
    @Test
    public void shouldChangeItemStatus() throws ItemNotFoundException {
        Order order = new Order(1, "11/11/1111", OrderStatus.MakingOrder,
                "2049 London Street", "", "My grocery items",
                new Customer(), new ArrayList<>(), null);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        GroceryItem groceryItem = new GroceryItem(1,"Test Item",new BigDecimal(100));
        Mockito.when(groceryItemRepository.findById(Mockito.any())).thenReturn(Optional.of(groceryItem));
        Item item = new Item(1,1, ItemStatus.Added,groceryItem);
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.of(item));
        itemService.setItemStatus(1,1,ItemStatus.Unavailable);
        Assertions.assertEquals(ItemStatus.Unavailable, itemService.findItemById(1).getStatus());
    }

    @Test
    public void shouldReplaceItem(){

    }

}
