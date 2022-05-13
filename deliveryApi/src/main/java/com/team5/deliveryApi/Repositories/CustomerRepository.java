package com.team5.deliveryApi.Repositories;

import com.team5.deliveryApi.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer save(Customer incomingCustomer);

    @Transactional
    @Modifying
    @Query(value ="update customers set email_subscribe= ?1 where id = ?2", nativeQuery = true)
    void updateEmailSubscriptionById(String email_subscribe, int id);
}
