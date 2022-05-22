package com.team5.deliveryApi.services;

import com.team5.deliveryApi.dto.ItemStatus;
import com.team5.deliveryApi.models.GroceryItem;
import com.team5.deliveryApi.models.Item;
import com.team5.deliveryApi.models.ItemNotFoundException;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.repositories.GroceryItemRepository;
import com.team5.deliveryApi.repositories.ItemRepository;
import com.team5.deliveryApi.repositories.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {
    private GroceryItemRepository groceryItemRepository;
    private ItemRepository itemRepository;
    private OrderRepository orderRepository;

    public ItemService(GroceryItemRepository groceryItemRepository, ItemRepository itemRepository,
                       OrderRepository orderRepository) {
        this.groceryItemRepository = groceryItemRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Sets the new status of an item in an order.
     * @param orderId The ID of the order containing the item.
     * @param itemId The ID of the item.
     * @param newStatus The new status of the order.
     * @return The Item object with the new status.
     * @throws ItemNotFoundException The item was not found in the order.
     */
     public Item setItemStatus(int orderId, int itemId, ItemStatus newStatus) throws ItemNotFoundException {
        Order order = orderRepository.getById(orderId);
        if (order != null){
            Optional<Item> cartItem = order.getItems().stream()
                    .filter(i -> i.getId() == itemId).findFirst();
            if (!cartItem.isPresent()) {
                throw new ItemNotFoundException();
            }
            cartItem.get().setStatus(newStatus);
            itemRepository.save(cartItem.get());
            return cartItem.get();
        }
         throw new ItemNotFoundException();
    }

    /**
     * Replace an item in an order with a new grocery item.
     * @param orderId The ID of the order.
     * @param oldItemId The ID of the item in the order to replace.
     * @param newItemId The ID of the grocery item to replace the item with.
     * @return The Item object containing the new grocery item.
     * @throws ItemNotFoundException The grocery item or cart item was not found.
     */
    public Item replaceItem(int orderId, int oldItemId, int newItemId) throws ItemNotFoundException {
        Order order = orderRepository.getById(orderId);
        if(order == null){
            throw new ItemNotFoundException();
        }
        Optional<Item> cartItem = order.getItems().stream().filter(i -> i.getId() == oldItemId).findFirst();
        if (!cartItem.isPresent()) {
            throw new ItemNotFoundException();
        }
       Optional<GroceryItem> groceryItem = groceryItemRepository.findById(newItemId);
        if (!groceryItem.isPresent()) {
            throw new ItemNotFoundException();
        }
        cartItem.get().setGroceryItem(groceryItem.get());
        itemRepository.save(cartItem.get());
        return cartItem.get();
    }

    public Item findItemById(int itemId){
        Item item = itemRepository.findById(itemId).get();
        return item;
    }

}
