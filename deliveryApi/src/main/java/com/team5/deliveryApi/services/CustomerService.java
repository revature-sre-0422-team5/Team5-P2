package com.team5.deliveryApi.services;

import com.team5.deliveryApi.dto.Credential;
import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.UserNotFoundException;
import com.team5.deliveryApi.repositories.CustomerRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;

    }

    /**
     * To save a customer
     * @param incomingCustomer The details of customer
     * @return boolean value
     */
    public boolean saveCustomer(Customer incomingCustomer) {
        customerRepository.save(incomingCustomer);
        return true;
    }


    public List<Customer> findAllCustomers(){
        List<Customer> customers=customerRepository.findAll();
        return customers;
    }

    public boolean login(Credential logindto) throws UserNotFoundException, IllegalStateException {

        boolean isSuccess = false;

        if (logindto.getUsername() == null || logindto.getPassword() == null) {
            throw new IllegalStateException("Username or password cannot be null");
        }

        Customer customer = customerRepository.findByUsername(logindto.getUsername());
        System.out.println(customer);

        if (customer == null) {
            log.info("User not found exception") ;
            throw new UserNotFoundException();
        }

        if (customer.getPassword().equals(logindto.getPassword())) {
            log.info("Login Success");
            isSuccess = true;
            customer.setIsloggedin(1);
            log.info("value="+customer.getIsloggedin());

        } else {
            log.info("password not matched");
        }
        return isSuccess;
    }

    public boolean logout(Credential logoutdto)  throws UserNotFoundException, IllegalStateException{
        boolean isSuccess = false;

       if (logoutdto.getUsername() == null) {
            throw new IllegalStateException("Username cannot be null ");
        }

      Customer customer = customerRepository.findByUsername(logoutdto.getUsername());

        if (customer == null) {
            log.info("Customer not found");
            throw new UserNotFoundException();
        }

        if (customer.getIsloggedin() == 0) {
            log.info("Customer logged out successfully");
            isSuccess = true;
            customer.setIsloggedin(1);
        } else {
            log.info("Customer not logged in");
        }
        return isSuccess;
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

