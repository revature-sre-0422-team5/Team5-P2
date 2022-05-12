package com.team5.deliveryApi.Services;

import com.team5.deliveryApi.Dto.Item;
import com.team5.deliveryApi.Dto.OrderLocation;
import com.team5.deliveryApi.Models.Order;
import com.team5.deliveryApi.Repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

   public Order findByOrderId(int odr_id) {

        Logger logger = LoggerFactory.getLogger(OrderService.class);
        logger.info("Getting Order by Id");

        Order outGoingOrder = orderRepository.findById(odr_id);

        if (outGoingOrder != null) {

            return outGoingOrder;
        } else { return null;
        }
    }
    public Order updateLocation(Order incomingOrder, OrderLocation incomingLocation){
        incomingOrder.setFrom_location(incomingLocation.getDto_from_location());
        incomingOrder.setDescription(incomingLocation.getDto_description());
        Order updatedOrder=orderRepository.save(incomingOrder);
        return updatedOrder;
    }
    public boolean removeItem(Order incomingOrder,int item_Id){
        if(incomingOrder.getItem_Id()==item_Id) {
            incomingOrder.setItem_Id(0);
            incomingOrder.setItem(null);
            incomingOrder.setItem_description(null);
            orderRepository.save(incomingOrder);
            return true;
        }
        else{return false;}
    }
    public Order addItem(Order incomingOrder, int item, Item dto_item) {
          incomingOrder.setItem_Id(item);
          incomingOrder.setItem(dto_item.getProductName());
          incomingOrder.setItem_description(dto_item.getProductDescription());
          Order updatedOrder=orderRepository.save(incomingOrder);
          return updatedOrder;

    }
    public boolean submitOrder(Order incomingOrder){
        incomingOrder.setStatus("Submitted");
        Order updatedOrder=orderRepository.save(incomingOrder);
        return true;
    }
    public boolean deleteOrder(Order incomingOrder){

        orderRepository.delete(incomingOrder);
        return true;
    }
}