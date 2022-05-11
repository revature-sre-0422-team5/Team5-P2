package com.team5.deliveryApi.Controllers;

import com.team5.deliveryApi.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity viewAllOrders(){
        return orderService.viewAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity viewOrderById(@PathVariable int id){
        return orderService.viewOrderById(id);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity viewStatusById(@PathVariable int id){
        return orderService.viewStatusById(id);
    }
}
