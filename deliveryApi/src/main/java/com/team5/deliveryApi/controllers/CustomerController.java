package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.UserNotFoundException;
import com.team5.deliveryApi.services.CustomerService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
        @Autowired
        CustomerService customerService;

        @PostMapping("/new")
        public String createCustomerAccount(@RequestBody Customer incomingCustomer) {

            /** To get logging message*/
            Logger logger = LoggerFactory.getLogger(CustomerController.class);
            boolean success = customerService.saveCustomer(incomingCustomer);
            logger.info("Adding new Customer");
            logger.info("incoming Customer"+ incomingCustomer);
            if (success ==true) {
                return "Account created successfully";

            } else {
                return "Error in creating new Customer Account";
            }
        }

        @GetMapping("/all")
        public ResponseEntity viewAllCustomer(){
            return customerService.viewAllCustomer();
        }

    /**
     * Sets the email subscription of a customer.
     * @param id The ID of the customer.
     * @param status The new subscription status of the customer.
     * @return The HTTP response containing the customer with the updated subscription status.
     */
    @PutMapping("/subscribe/{id}")
    public ResponseEntity<Customer> subscribeEmailById(@PathVariable int id,
                                                       @PathParam("status") boolean status) {
        try {
            return ResponseEntity.ok(customerService.updateEmailSubscription(id, status));
        } catch (UserNotFoundException e) {
            log.error("[PUT] User with ID " + id + " was not found.");
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
