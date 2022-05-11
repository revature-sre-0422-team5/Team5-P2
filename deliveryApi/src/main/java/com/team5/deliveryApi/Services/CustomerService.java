package com.team5.deliveryApi.Services;

import com.team5.deliveryApi.Models.Customer;
import com.team5.deliveryApi.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;
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


    }

