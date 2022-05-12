package com.team5.deliveryApi.Services;

import com.team5.deliveryApi.Models.Item;
import com.team5.deliveryApi.Models.ItemNotFoundException;
import com.team5.deliveryApi.Models.ItemStatus;
import com.team5.deliveryApi.Models.Order;
import com.team5.deliveryApi.Repositories.ItemRepository;
import com.team5.deliveryApi.Repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private OrderRepository orderRepository;

    public ItemService(ItemRepository itemRepository, OrderRepository orderRepository){
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Sets the new status of an item in an order.
     * @param orderid The ID of the order containing the item.
     * @param itemid The ID of the item.
     * @param newStatus The new status of the order.
     * @return The Order object with the new updated status.
     */
    public Item setItemStatus(int orderid, int itemid, ItemStatus newStatus) throws ItemNotFoundException {
        Order order = orderRepository.getById(orderid);
        Optional<Item> item = order.getItems().stream().filter(i -> i.getId() == itemid).findFirst();
        if (!item.isPresent()) {
            throw new ItemNotFoundException();
        }
        item.get().setStatus(newStatus);
        return item.get();
    }
}
