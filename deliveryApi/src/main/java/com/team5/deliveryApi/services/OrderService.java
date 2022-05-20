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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class OrderService {

    @Value("${api.notification}")
    private String notificationApiUrl;
    @Autowired
    private RestTemplate restTemplate;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
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

    /**
     * to find order by its id
     * @param odrId refers to the id of the order
     * @return the order with the given id
     */
    public Order findByOrderId(int odrId) {
        Order outGoingOrder = orderRepository.findById(odrId).get();
        return outGoingOrder;
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
        Item item = incomingOrder.getItems().stream().filter(i -> i.getGroceryItem().getId() == itemId).findFirst().get();
        itemRepository.delete(item);
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
        if (order.getCustomer().isEmail_subscribe()) {
            try {
                if (status.equals(OrderStatus.Submitted)) {
                    sendNotification(order.getCustomer().getEmail(),
                            "Your order has been submitted",
                            "Order number: " + orderId + "\n" +
                                    "Your order has been successfully submitted.\n\n");
                } else if (status.equals(OrderStatus.Delivered)) {
                    sendNotification(order.getCustomer().getEmail(),
                            "Your order has been delivered",
                            "Order number: " + orderId + "\n" +
                                    "Your order has been successfully delivered.\n\n" +
                                    "How do you like the order?  Give us a review!");
                }
            } catch (Exception e) {
                log.error("Failed to send email notification.");
                e.printStackTrace();
            }
        }
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

    /**
     * Send a email notification message to a user.
     * @param email The email to send the notification to.
     * @param subject The subject of the email.
     * @param message The body message of the email.
     */
    public ResponseEntity<Object> sendNotification(String email, String subject, String message) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Boolean> uriParam = new HashMap<>();
        map.put("recipient", email);
        map.put("subject", subject);
        map.put("message", message);
        uriParam.put("html", false);
        return restTemplate.postForEntity(notificationApiUrl + "?html={html}", map, Object.class, uriParam);
    }
}