package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.GroceryItem;
import com.team5.deliveryApi.services.GroceryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/grocery")
public class GroceryController {

    @Autowired
    private GroceryService groceryService;

    /**
     * Gets all available grocery items.
     * @return The HTTP response containing all the grocery items.
     */
    @GetMapping ("/get-all")
    public ResponseEntity<List<GroceryItem>> getGroceries() {
        return ResponseEntity.ok(groceryService.getGroceries());
    }

    /**
     * Adds a new grocery item into the available list.
     * @param groceryItem The new grocery item to add.
     * @return The HTTP response containing the newly added grocery item.
     */
    @PostMapping ("/add-grocery-item")
    public ResponseEntity<GroceryItem> addGrocery(@RequestBody GroceryItem groceryItem) {
        return ResponseEntity.ok(groceryService.addGrocery(groceryItem));
    }
}
