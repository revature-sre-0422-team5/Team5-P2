package com.team5.deliveryApi.services;

import com.team5.deliveryApi.dto.ItemStatus;
import com.team5.deliveryApi.dto.OrderStatus;
import com.team5.deliveryApi.models.GroceryItem;
import com.team5.deliveryApi.models.Item;
import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.dto.OrderLocation;
import com.team5.deliveryApi.repositories.GroceryItemRepository;
import com.team5.deliveryApi.repositories.ItemRepository;
import com.team5.deliveryApi.repositories.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.repositories.CustomerRepository;

import com.team5.deliveryApi.repositories.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class OrderService {

    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private GroceryItemRepository groceryItemRepository;
    private ShopperRepository shopperRepository;
    @Autowired
    private ItemRepository itemRepository;
    public OrderService(CustomerRepository customerRepository, OrderRepository orderRepository,
                        ShopperRepository shopperRepository, GroceryItemRepository groceryItemRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.shopperRepository = shopperRepository;
        this.groceryItemRepository = groceryItemRepository;
    }


    public ResponseEntity viewAllOrders(){
        return ResponseEntity.ok(orderRepository.findAll());
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

    public Order viewOrderById(int id) {
        Order outGoingOrder = orderRepository.findById(id).get();
        return outGoingOrder;
    }
    public Order findByOrderId(int odrId) {
        Order outGoingOrder = orderRepository.findById(odrId).get();
        log.info("AMOUNT OF ITEMS:  " + String.valueOf(outGoingOrder.getItems().size()));
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

        /*Optional<Item> cartItem = incomingOrder.getItems().stream().filter(i -> i.getId() == item_Id).findFirst();

           // itemRepository.delete(cartItem);*/
            orderRepository.save(incomingOrder);
            return true;

    }

    public Order addItem(int odrID,int gItemID,int qnty) {
        GroceryItem groceryItem = groceryItemRepository.findById(gItemID).get();
        Order order = orderRepository.findById(odrID).get();
        Item item = itemRepository.save(new Item(qnty, ItemStatus.Added,groceryItem));
        order.getItems().add(item);
        return orderRepository.save(order);
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