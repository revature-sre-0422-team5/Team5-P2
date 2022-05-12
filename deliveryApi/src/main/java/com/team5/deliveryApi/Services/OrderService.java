package com.team5.deliveryApi.Services;

import com.team5.deliveryApi.Models.Order;
import com.team5.deliveryApi.Models.OrderStatus;
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

    /**
     * Sets the new status of the order.
     * @param id The ID of the order.
     * @param newStatus The new status of the order.
     * @return The Order object with the new updated status.
     */
    public Order setOrderStatus(int id, OrderStatus newStatus) {
        Order order = orderRepository.getById(id);
        order.setStatus(newStatus);
        return order;
    }

    //TO DO
    public void payOrder(int id){
        orderRepository.updateStatusById("Paid",id);
        //update status paid?
    }

    public void subscribeEmail(int id){
    }

    public boolean saveOrder(Order incomingOrder) {
        orderRepository.save(incomingOrder);
        return true;
    }

}
