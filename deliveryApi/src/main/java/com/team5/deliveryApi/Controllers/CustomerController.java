package com.team5.deliveryApi.Controllers;

import com.team5.deliveryApi.Models.Customer;
import com.team5.deliveryApi.Models.Order;
import com.team5.deliveryApi.Services.CustomerService;
import com.team5.deliveryApi.Services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Customer")
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
}
