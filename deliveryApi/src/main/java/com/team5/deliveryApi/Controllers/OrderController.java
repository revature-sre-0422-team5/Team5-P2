package com.team5.deliveryApi.Controllers;

import com.team5.deliveryApi.Models.Order;
import com.team5.deliveryApi.Services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/new")
    public String createOrder(@RequestBody Order incomingOrder) {

        /** To get logging message*/
        Logger logger = LoggerFactory.getLogger(OrderController.class);
        logger.info("Adding new Order");
        boolean success=orderService.saveOrder(incomingOrder);
        logger.info("incoming order"+incomingOrder);
        if (success ==true) {
            return "Successfully added";

        } else {
            return "Error in creating new Order";
        }


    }
}
