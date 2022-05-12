package com.team5.deliveryApi.Controllers;

import com.team5.deliveryApi.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.team5.deliveryApi.Models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @GetMapping("/all")
    public ResponseEntity viewAllOrders(){
        return orderService.viewAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity viewOrderById(@PathVariable int id){
        return orderService.viewOrderById(id);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity viewStatusById(@PathVariable int id) {
        return orderService.viewStatusById(id);
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity payOrderById(@PathVariable int id){
        return ResponseEntity.ok(orderService.payOrder(id));
    }
}
