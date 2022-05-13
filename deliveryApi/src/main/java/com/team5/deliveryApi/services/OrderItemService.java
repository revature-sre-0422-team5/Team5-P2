package com.team5.deliveryApi.services;

import com.team5.deliveryApi.models.OrderItem;
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
    public OrderItem addItem (String itemName, long itemPriceInCents, String itemDescription){
        try {
            if (itemName.equals("") || itemDescription.equals("")){
                throw new Exception("OrderItemService - attempted to create an object with an invalid name or description");
            }

            log.info("OrderItemService - adding a new item");

            OrderItem savingOrderItem = new OrderItem(0, itemName, itemDescription, itemPriceInCents, "AVAILABLE");
            oir.save(savingOrderItem);
            return savingOrderItem;
        }
        catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    public void updateItemStatus (String status, int itemReferenceNumber){
        try {
            if (status.equals("")){
                throw new Exception("OrderItemService - attempted to update an object with an invalid status");
            }

            log.info("OrderItemService - updating item: " + itemReferenceNumber + " status to " + status);
            oir.updateItemStatus(status, itemReferenceNumber);
        }
        catch (Exception e){
            log.error(e.toString());
        }
    }



}
