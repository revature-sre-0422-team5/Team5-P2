package com.team5.deliveryApi.repositories;

import com.team5.deliveryApi.models.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Modifying
    @Query ("update OrderItem item set item.itemStatus = ?1 where item.itemId = ?2")
    void updateItemStatus(String itemStatuts, Integer itemId);
    
}
