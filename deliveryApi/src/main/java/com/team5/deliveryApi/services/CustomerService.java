package com.team5.deliveryApi.services;

import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.repositories.CustomerRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

        public ResponseEntity subscribeEmailById(int id){
            customerRepository.updateEmailSubscriptionById("yes",id);
            return ResponseEntity.ok(true);
        }

        public ResponseEntity unsubscribeEmailById(int id){
            customerRepository.updateEmailSubscriptionById("no",id);
            return ResponseEntity.ok(true);
        }


    }

