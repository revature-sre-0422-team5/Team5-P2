package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.OrderItem;
import com.team5.deliveryApi.dto.OrderItemAdd;
import com.team5.deliveryApi.dto.OrderItemUpdate;
import com.team5.deliveryApi.services.OrderItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class OrderItemController {

    @Autowired
    private OrderItemService ois;
    
    @RequestMapping("/items/add")
    public ResponseEntity<OrderItem> addItem(@RequestBody OrderItemAdd oia){
        try {
            log.info("OrderItemController - Adding an item");
            OrderItem savingOrderItem = ois.addItem(oia.getItemName(), oia.getItemPriceInCents(), oia.getItemDescription());
            return ResponseEntity.ok().body(savingOrderItem);
       }
       catch (Exception e){
           log.error("OrderItemController - Something went wrong adding an item");
           log.error(e.toString());
           return ResponseEntity.internalServerError().body(null);
        }
    }

    @RequestMapping ("/items/updatestatus")
    public ResponseEntity updateItem (@RequestBody OrderItemUpdate oiu){
        try {
            log.info ("OrderItemController - Updating item status");
            ois.updateItemStatus(oiu.getOrderStatus(), oiu.getOrderItemId());
            return ResponseEntity.ok().body("Updated item");
        }
        catch (Exception e){
            log.error("OrderItemController - Something went wrong updating item");
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Unable to process request at the time");
        }
    }

}
