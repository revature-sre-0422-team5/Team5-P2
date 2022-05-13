package com.team5.deliveryApi.services;

import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.UserNotFoundException;
import com.team5.deliveryApi.repositories.CustomerRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    public boolean saveCustomer(Customer incominngCustomer) {
        customerRepository.save(incominngCustomer);
        return true;
    }

    public ResponseEntity viewAllCustomer(){
        return ResponseEntity.ok(customerRepository.findAll());
    }
    
    public Optional<Customer> viewCustomerById(int id){
        return customerRepository.findById(id);
    }

    /**
     * Updates the email subscription status of a customer.
     * @param id The ID of the customer.
     * @param status The new email subscription status.
     * @return The customer with the new updated subscription status.
     * @throws UserNotFoundException The user was not found in the repository.
     */
    public Customer updateEmailSubscription(int id, boolean status) throws UserNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new UserNotFoundException();
        }
        customer.get().setEmail_subscribe(status);
        customerRepository.save(customer.get());
        return customer.get();
    }
}

