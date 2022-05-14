package com.team5.deliveryApi.services;

import com.team5.deliveryApi.models.GroceryItem;
import com.team5.deliveryApi.repositories.GroceryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryService {

    private GroceryItemRepository groceryItemRepository;

    public GroceryService(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    /**
     * Gets the list of all the grocery items available.
     * @return The list of all grocery items.
     */
    public List<GroceryItem> getGroceries() {
        return groceryItemRepository.findAll();
    }

    /**
     * Adds a new grocery item into the available groceries.
     * @param groceryItem The new grocery item to add.
     * @return The newly saved grocery item.
     */
    public GroceryItem addGrocery(GroceryItem groceryItem) {
        return groceryItemRepository.save(groceryItem);
    }

}
