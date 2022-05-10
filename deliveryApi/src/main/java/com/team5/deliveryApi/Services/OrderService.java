package com.team5.deliveryApi.Services;

import com.team5.deliveryApi.Models.Order;
import com.team5.deliveryApi.Repository.OrderRepo;

import java.util.List;
import java.util.Optional;

public class OrderService {

    private OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo){
        super();
        this.orderRepo = orderRepo;
    }

    public List<Order> viewAllOrders(){
        return orderRepo.findAll();
    }

    public Optional<Order> viewOrderById(int id){
        return orderRepo.findById(id);
    }

    public String viewStatusById(int id){
        return orderRepo.findById(id).get().getStatus();
    }

    //TO DO
    public void payOrder(int id){
        //update status paid?
    }

    public void subscribeEmail(int id){
    }

}
