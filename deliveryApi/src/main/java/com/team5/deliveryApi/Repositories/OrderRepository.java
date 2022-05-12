package com.team5.deliveryApi.Repositories;

import com.team5.deliveryApi.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    Order save(Order incomingOrder);

    Optional<Order> findById(Integer id);

    @Transactional
    @Modifying
    @Query(value ="update orders set status= ?1 where id = ?2", nativeQuery = true)
    void updateStatusById(String status, int id);

    @Transactional
    @Modifying
    @Query(value ="update orders set order_status= ?1 where id = ?2", nativeQuery = true)
    void updateOrderStatusById(String status, int id);
}
