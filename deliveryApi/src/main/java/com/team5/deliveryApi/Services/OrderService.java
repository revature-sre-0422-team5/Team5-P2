package com.team5.deliveryApi.Services;

import com.team5.deliveryApi.Models.Order;
import com.team5.deliveryApi.Repositories.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        super();
        this.orderRepository = orderRepository;
    }

    public ResponseEntity viewAllOrders(){
        return ResponseEntity.ok(orderRepository.findAll());
    }

    public ResponseEntity viewOrderById(int id){
        return ResponseEntity.ok(orderRepository.findById(id).get());
    }

    public ResponseEntity viewStatusById(int id){
        return ResponseEntity.ok(orderRepository.findById(id).get().getStatus());
    }

    public boolean payOrder(int id){
        orderRepository.updatePayStatusById("Paid",id);
        return true;
    }

    public boolean saveOrder(Order incomingOrder) {
        orderRepository.save(incomingOrder);
        return true;
    }

}
