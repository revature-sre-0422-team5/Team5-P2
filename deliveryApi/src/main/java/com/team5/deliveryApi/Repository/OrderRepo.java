package com.team5.deliveryApi.Repository;

import com.team5.deliveryApi.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepo  extends JpaRepository<Order, Integer> {

    Optional<Order> findById(Integer id);
}
