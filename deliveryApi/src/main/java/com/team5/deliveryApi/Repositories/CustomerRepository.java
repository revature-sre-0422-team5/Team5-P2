package com.team5.deliveryApi.Repositories;

import com.team5.deliveryApi.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
