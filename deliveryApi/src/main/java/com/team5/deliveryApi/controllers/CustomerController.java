package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.dto.credential;
import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.UserNotFoundException;
import com.team5.deliveryApi.repositories.CustomerRepository;
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
        @Autowired
    CustomerRepository customerRepository;

    /**
     * Create account for a new customer
     * @param incomingCustomer refers to details of new customer
     * @return Successful or Error message
     */
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

    @GetMapping("/login")
    public ResponseEntity login(@RequestBody credential logindto) {
        try {

            boolean isSuccess = false;
            isSuccess = customerService.login(logindto);

            if(isSuccess) {
                return ResponseEntity.ok().body("User successfully logged in");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error login ");
        }
    }
    @GetMapping("/logout")
    public ResponseEntity logout(@RequestBody credential logoutdto) {
        try {
            log.info("CustomerController - /logout");
            boolean isSuccess = false;
            isSuccess = customerService.logout(logoutdto);

            if(isSuccess) {
                return ResponseEntity.ok().body("User logged out");
            } else {
                return ResponseEntity.ok().body("User was not logged in - no action taken");
            }
        } catch (Exception e) {
            log.warn("Customer Controller - catch block for logout");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error logging out customer");
        }
    }



        @GetMapping("/all")
        public ResponseEntity viewAllCustomer(){

            return ResponseEntity.ok(customerService.findAllCustomers());
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
