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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class OrderService {

    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    Logger logger = LoggerFactory.getLogger(OrderService.class);
    private GroceryItemRepository groceryItemRepository;
    private ItemRepository itemRepository;
    private ShopperRepository shopperRepository;

    public OrderService(CustomerRepository customerRepository, OrderRepository orderRepository,
                        ShopperRepository shopperRepository,GroceryItemRepository groceryItemRepository,ItemRepository itemRepository) {
        super();
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.shopperRepository = shopperRepository;
        this.groceryItemRepository=groceryItemRepository;
        this.itemRepository=itemRepository;
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
        orderRepository.save(incomingOrder);
        Customer customer = customerRepository.getById(customerId);
        customer.getOrders().add(incomingOrder);
        incomingOrder.setCustomer(customer);
        orderRepository.save(incomingOrder);
        return true;
    }

<<<<<<< HEAD


    public Order findByOrderId(int odrId) {
        Order outGoingOrder = orderRepository.findById(odrId).get();
        if (outGoingOrder != null) {

            return outGoingOrder;
        } else { return null;
        }
=======
    /**
     * to find order by its id
     * @param odrId refers to the id of the order
     * @return the order with the given id
     */
    public Order findByOrderId(int odrId) {
        Order outGoingOrder = orderRepository.findById(odrId).get();

        return outGoingOrder;
>>>>>>> 0d21bcb1d556025e628213d0e84fd620a9d0a8ab
    }

    /**
     * To update the location of store
     * @param incomingOrder refers to which order the location is to be updated
     * @param incomingLocation refers to location description of the store
     * @return updated order
     */
    public Order updateLocation(Order incomingOrder, OrderLocation incomingLocation){
        incomingOrder.getCustomer().setLocation(incomingLocation.getDto_from_location());
        incomingOrder.setDescription(incomingLocation.getDto_description());
        Order updatedOrder=orderRepository.save(incomingOrder);
        return updatedOrder;
    }

    /**
     * Remove an item from the order
     * @param incomingOrder refers to the order from which item to be removed
     * @param itemId refers to item id
     * @return boolean value
     */
    public boolean removeItem(Order incomingOrder,int itemId){

       // Item item = incomingOrder.getItems().stream().filter(i -> i.getId() == itemId).findFirst().get();
        Item item = incomingOrder.getItems().stream().filter(i -> i.getGroceryItem().getId() == itemId).findFirst().get();
        log.info("checking"+item+incomingOrder);
        itemRepository.delete(item);

        itemRepository.save(item);
        log.info("checking"+item+incomingOrder);
        return true;

    }


    /**
     * Adding items to order
     * @param odrID  refers to order id
     * @param gItemID refers to grocery item id
     * @param qnty refers to quantity of grocery item
     * @return updated order
     */
    public Order addItem(int odrID,int gItemID,int qnty) {
        log.info("checking"+odrID+gItemID+qnty);
        GroceryItem groceryItem=groceryItemRepository.findById(gItemID).get();
        log.info("Checking Grocery Item"+groceryItem);
        Order order=orderRepository.findById(odrID).get();
        log.info("order"+order+odrID);

        Item item =new Item(qnty, ItemStatus.Added,groceryItem);
        log.info("item"+item);
        itemRepository.save(item);
        order.getItems().add(item);
        log.info("Added Order"+order);
        orderRepository.save(order);
        return (order);
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