package com.team5.api2.Repositories;

import com.team5.api2.entities.OrderPaymentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRequestRepository extends JpaRepository<OrderPaymentEntity,Integer>{
    
}
