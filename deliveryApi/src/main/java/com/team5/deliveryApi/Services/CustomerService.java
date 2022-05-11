package com.team5.deliveryApi.Services;

import com.team5.deliveryApi.Models.Customer;
import com.team5.deliveryApi.Repositories.CustomerRepository;
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

        public List<Customer> viewCustomer(){
            return customerRepository.findAll();
        }
    
        public Optional<Customer> viewCustomerById(int id){
            return customerRepository.findById(id);
        }


    }

