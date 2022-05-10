package com.team5.deliveryApi.Repository;

import com.team5.deliveryApi.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Optional<Customer> findById(Integer id);
}
