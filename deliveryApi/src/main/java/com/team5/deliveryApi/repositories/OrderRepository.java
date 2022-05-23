package com.team5.deliveryApi.repositories;

import com.team5.deliveryApi.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Transactional
    @Modifying
    @Query(value ="update orders set status= ?1 where order_id = ?2", nativeQuery = true)
    void updateOrderStatusById(String status, int id);

    @Transactional
    @Modifying
    @Query(value ="update orders set pay_status= ?1 where order_id = ?2", nativeQuery = true)
    void updatePayStatusById(String status, int id);


}
