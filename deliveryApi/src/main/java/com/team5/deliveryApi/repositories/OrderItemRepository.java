package com.team5.deliveryApi.repositories;

import com.team5.deliveryApi.Models.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    
}
