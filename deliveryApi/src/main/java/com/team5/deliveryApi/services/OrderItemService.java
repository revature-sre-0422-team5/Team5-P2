package com.team5.deliveryApi.services;

import com.team5.deliveryApi.Models.OrderItem;
import com.team5.deliveryApi.repositories.OrderItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderItemService {
    @Autowired    
    private OrderItemRepository oir;


    /**
     * Save a new order item with the default status of available
     * @param itemName
     * @param itemDescription
     */
    public void addItem (String itemName, String itemDescription){
        try {
            log.info("OrderItemService - adding a new item");
            oir.save(new OrderItem(0, itemName, itemDescription, "AVAILABLE"));
        }
        catch (Exception e){
            log.error(e.toString());
        }
    }

    public void updateItemStatus (String status, int itemReferenceNumber){
        try {
            log.info("OrderItemService - updating item: " + itemReferenceNumber + " status to " + status);
            oir.updateItemStatus(status, itemReferenceNumber);
        }
        catch (Exception e){
            log.error(e.toString());
        }
    }

}
