package com.team5.deliveryApi.Services;

import com.team5.deliveryApi.Models.Customer;
import com.team5.deliveryApi.Repository.CustomerRepo;

import java.util.List;
import java.util.Optional;

public class CustomerService {

    private CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo){
        super();
        this.customerRepo = customerRepo;
    }

    public List<Customer> viewCustomer(){
        return customerRepo.findAll();
    }

    public Optional<Customer> viewCustomerById(int id){
        return customerRepo.findById(id);
    }


}
