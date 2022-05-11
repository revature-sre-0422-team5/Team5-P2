package com.team5.deliveryApi.Repository;

import com.team5.deliveryApi.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface OrderRepo  extends JpaRepository<Order, Integer> {

    Optional<Order> findById(Integer id);

    @Transactional
    @Modifying
    @Query(value ="update orders set status= ?1 where id = ?2", nativeQuery = true)
    void updateStatusById(String status, int id);
}
