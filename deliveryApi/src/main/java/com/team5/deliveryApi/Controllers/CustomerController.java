package com.team5.deliveryApi.Controllers;

import com.team5.deliveryApi.Models.Customer;
import com.team5.deliveryApi.Services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Customer")
public class CustomerController {
        @Autowired
        CustomerService customerService;

        @PostMapping("/new")
        public String createCustomerAccount(@RequestBody Customer incomingCustomer) {

            /** To get logging message*/
            Logger logger = LoggerFactory.getLogger(CustomerController.class);
            boolean success=customerService.saveCustomer(incomingCustomer);
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

        @PutMapping("/subscribe/{id}")
        public ResponseEntity subscribeEmailById(@PathVariable int id){
            return  customerService.subscribeEmailById(id);
        }


        @PutMapping("/unsubscribe/{id}")
        public ResponseEntity unsubscribeEmailById(@PathVariable int id){
            return  customerService.unsubscribeEmailById(id);
        }

}
