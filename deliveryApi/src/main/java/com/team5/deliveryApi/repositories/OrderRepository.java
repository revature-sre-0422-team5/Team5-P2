package com.team5.deliveryApi.repositories;

import com.team5.deliveryApi.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    Order save(Order incomingOrder);


    @Transactional
    @Modifying
    @Query(value ="update orders set status= ?1 where order_id = ?2", nativeQuery = true)
    void updateOrderStatusById(String status, int id);

    @Transactional
    @Modifying
    @Query(value ="update orders set pay_status= ?1 where order_id = ?2", nativeQuery = true)
    void updatePayStatusById(String status, int id);

    Order findById(int odr_id);
    
    void delete(Order incomingOrder);
}
