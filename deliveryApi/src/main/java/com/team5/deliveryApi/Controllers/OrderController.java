package com.team5.deliveryApi.Controllers;

import com.team5.deliveryApi.Models.OrderStatus;
import com.team5.deliveryApi.Services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.team5.deliveryApi.Models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("/order")
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

    /**
     * Updates the status of an order.
     * @param id The ID of the order to update.
     * @param newStatus The new status of the order.
     * @return The ResponseEntity containing the newly updated order.
     */
    @PutMapping("/status/{id}")
    public ResponseEntity<Order> setOrderStatus(@PathVariable int id,
                                           @PathParam("status") OrderStatus newStatus) {
        ResponseEntity<Order> response = ResponseEntity.ok(orderService.setOrderStatus(id, newStatus));
        log.info("[PUT] Order " + id + " has been updated with new status " + newStatus + ".");
        return response;
    }

    @GetMapping("/status/{id}")
    public ResponseEntity viewStatusById(@PathVariable int id) {
        return orderService.viewStatusById(id);
    }
}
