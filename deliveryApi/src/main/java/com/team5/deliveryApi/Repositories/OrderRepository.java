package com.team5.deliveryApi.Repositories;

import com.team5.deliveryApi.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order save(Order incomingOrder);
    Order findById(int odr_id);
    void delete(Order incomingOrder);
}
