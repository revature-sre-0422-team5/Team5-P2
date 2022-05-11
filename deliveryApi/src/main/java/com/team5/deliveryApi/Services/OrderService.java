package com.team5.deliveryApi.Services;

import com.team5.deliveryApi.Models.Order;
import com.team5.deliveryApi.Repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        super();
        this.orderRepository = orderRepository;
    }


    public boolean saveOrder(Order incomingOrder) {
        orderRepository.save(incomingOrder);

        return true;
    }


}
