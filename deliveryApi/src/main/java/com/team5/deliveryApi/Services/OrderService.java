package com.team5.deliveryApi.Services;

import com.team5.deliveryApi.Models.Order;
import com.team5.deliveryApi.Repository.OrderRepo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class OrderService {

    private OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo){
        super();
        this.orderRepo = orderRepo;
    }

    public ResponseEntity viewAllOrders(){
        return ResponseEntity.ok(orderRepo.findAll());
    }

    public ResponseEntity viewOrderById(int id){
        return ResponseEntity.ok(orderRepo.findById(id).get());
    }

    public ResponseEntity viewStatusById(int id){
        return ResponseEntity.ok(orderRepo.findById(id).get().getStatus());
    }

    //TO DO
    public void payOrder(int id){
        orderRepo.updateStatusById("Paid",id);
        //update status paid?
    }

    public void subscribeEmail(int id){
    }

}
