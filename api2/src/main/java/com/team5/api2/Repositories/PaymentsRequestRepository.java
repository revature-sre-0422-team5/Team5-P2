package com.team5.api2.Repositories;

import java.util.List;

import com.team5.api2.models.OrderPaymentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRequestRepository extends JpaRepository<OrderPaymentEntity,Integer>{
    @Query (value = "SELECT * from order_payments u where u.stripe_charge_id = ?1", nativeQuery = true)
    List<OrderPaymentEntity> findByStripeId (String stripeChargeId);
}
