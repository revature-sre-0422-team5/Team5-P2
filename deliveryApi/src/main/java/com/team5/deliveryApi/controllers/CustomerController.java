package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.dto.Credential;
import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.UserNotFoundException;
import com.team5.deliveryApi.services.CustomerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Create account for a new customer
     * @param incomingCustomer refers to details of new customer
     * @return Successful or Error message
     */
        @PostMapping("/new")
        public String createCustomerAccount(@RequestBody Customer incomingCustomer) {
            boolean success = customerService.saveCustomer(incomingCustomer);
            log.info("Adding new Customer: " + incomingCustomer);
            if (success ==true) {
                return "Account created successfully";

            } else {
                return "Error in creating new Customer Account";
            }
        }

    /**
     * Login for customer
     * @param logindto credentials to login
     * @return Response message
     */
    @GetMapping("/login")
    public ResponseEntity login(@RequestBody Credential logindto) {
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

    /**
     * To logout for customer
     * @param logoutdto credentials to logout
     * @return Response message
     */
    @GetMapping("/logout")
    public ResponseEntity logout(@RequestBody Credential logoutdto) {
        try {
            log.info("CustomerController -logout");
            boolean isSuccess = false;
            isSuccess = customerService.logout(logoutdto);

            if(isSuccess) {
                return ResponseEntity.ok().body("User logged out");
            } else {
                return ResponseEntity.ok().body("User was not logged in ");
            }
        } catch (Exception e) {
            log.info("Customer Controller - Exception");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error logging out ");
        }
    }

    /**
     * To get all customers
     * @return Return all customers
     */

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
            log.error("User with ID " + id + " was not found.");
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
