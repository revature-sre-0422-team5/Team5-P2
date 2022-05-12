package com.team5.deliveryApi.Controllers;

import com.team5.deliveryApi.Models.Item;
import com.team5.deliveryApi.Models.ItemNotFoundException;
import com.team5.deliveryApi.Models.ItemStatus;
import com.team5.deliveryApi.Models.Order;
import com.team5.deliveryApi.Services.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * Updates the status of an item in an order.
     * @param itemId The ID of the item to update.
     * @param orderId The ID of the order containing the item.
     * @param newStatus The new status of the order.
     * @return The ResponseEntity containing the newly updated order.
     */
    @PutMapping("/status/{id}")
    public ResponseEntity<Item> setOrderStatus(@PathVariable int itemId,
                                               @PathVariable int orderId,
                                               @PathParam("status") ItemStatus newStatus) {
        try {
            ResponseEntity<Item> response = ResponseEntity.ok(itemService.setItemStatus(orderId, itemId, newStatus));
            log.info("Item " + itemId + " has been updated with new status " + newStatus + ".");
            return response;
        } catch (ItemNotFoundException e) {
            log.error("Item " + itemId + " was not found with the order.");
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}