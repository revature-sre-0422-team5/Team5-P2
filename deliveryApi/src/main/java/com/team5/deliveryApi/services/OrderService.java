package com.team5.deliveryApi.services;

import com.team5.deliveryApi.dto.OrderStatus;
import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.dto.Item;
import com.team5.deliveryApi.dto.OrderLocation;
import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.repositories.CustomerRepository;
import com.team5.deliveryApi.repositories.OrderRepository;

import com.team5.deliveryApi.repositories.ShopperRepository;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private ShopperRepository shopperRepository;

    public OrderService(CustomerRepository customerRepository, OrderRepository orderRepository,
                        ShopperRepository shopperRepository) {
        super();
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.shopperRepository = shopperRepository;
    }

    public ResponseEntity viewAllOrders(){
        return ResponseEntity.ok(orderRepository.findAll());
    }

    public ResponseEntity viewOrderById(int id){
        return ResponseEntity.ok(orderRepository.findById(id));
    }

    public ResponseEntity viewStatusById(int id){
        return ResponseEntity.ok(orderRepository.findById(id).get().getStatus());
    }

    public boolean payOrder(int id){
        orderRepository.findById(id).get().setPay_status("Paid");
        return true;
    }

    /**
     * Saves an order from a customer.
     * @param customerId The customer who created the order.
     * @param incomingOrder The newly created order.
     * @return
     */
    public boolean saveOrder(int customerId, Order incomingOrder) {
        Customer customer = customerRepository.getById(customerId);
        customer.getOrders().add(incomingOrder);
        incomingOrder.setCustomer(customer);
        orderRepository.save(incomingOrder);
        return true;
    }
   public Order findByOrderId(int odr_id) {

        Logger logger = LoggerFactory.getLogger(OrderService.class);
        logger.info("Getting Order by Id");

        Order outGoingOrder = orderRepository.findById(odr_id).get();

        if (outGoingOrder != null) {

            return outGoingOrder;
        } else { return null;
        }
    }

    public Order updateLocation(Order incomingOrder, OrderLocation incomingLocation){
        incomingOrder.getCustomer().setLocation(incomingLocation.getDto_from_location());
        incomingOrder.setDescription(incomingLocation.getDto_description());
        Order updatedOrder=orderRepository.save(incomingOrder);
        return updatedOrder;
    }


    public boolean removeItem(Order incomingOrder,int item_Id){
        /*
        if(incomingOrder.getItem_Id()==item_Id) {
            incomingOrder.setItem_Id(0);
            incomingOrder.setItem(null);
            incomingOrder.setItem_description(null);
            orderRepository.save(incomingOrder);
            return true;
        }
        else{return false;}

         */
        return true;
    }
    public Order addItem(Order incomingOrder, int item, Item dto_item) {
        /*
          incomingOrder.setItem_Id(item);
          incomingOrder.setItem(dto_item.getProductName());
          incomingOrder.setItem_description(dto_item.getProductDescription());
          Order updatedOrder=orderRepository.save(incomingOrder);
          return updatedOrder;

         */
        return null;
    }


    /**
     * Update the status of an order.
     * @param orderId The ID of the order to update.
     * @return The order with an updated status.
     */
    public Order updateOrderStatus(int orderId, OrderStatus status) {
        Order order = findByOrderId(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
    public boolean deleteOrder(Order incomingOrder) {

        orderRepository.delete(incomingOrder);
        return true;
    }

    /**
     * Assign a shopper to an order.
     * @param orderId The ID of the order.
     * @param shopperId The ID of the shopper.
     * @return The updated order with the newly assigned shopper.
     */
    public Order assignShopper(int orderId, int shopperId) {
        Optional<Order> order = orderRepository.findById(orderId);
        Optional<Shopper> shopper = shopperRepository.findById(shopperId);
        order.get().setShopper(shopper.get());
        return order.get();
    }
}