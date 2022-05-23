package com.team5.deliveryApi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.deliveryApi.dto.ItemStatus;
import com.team5.deliveryApi.dto.OrderLocation;
import com.team5.deliveryApi.dto.OrderStatus;
import com.team5.deliveryApi.models.GroceryItem;
import com.team5.deliveryApi.models.Item;
import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.Order;

import com.team5.deliveryApi.repositories.GroceryItemRepository;
import com.team5.deliveryApi.repositories.ItemRepository;
import com.team5.deliveryApi.repositories.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import com.team5.deliveryApi.models.Shopper;
import com.team5.deliveryApi.repositories.CustomerRepository;

import com.team5.deliveryApi.repositories.ShopperRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {
    @Value("${api.notification:none}")
    private String notificationApiUrl;

    @Value("${api.directions}")
    private String api2Url;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GroceryItemRepository groceryItemRepository;
    @Autowired
    private ShopperRepository shopperRepository;
    @Autowired
    private ItemRepository itemRepository;

    public List<Order> viewAllOrders(){
        return (orderRepository.findAll());
    }


    public OrderStatus viewStatusById(int id){
        return orderRepository.findById(id).get().getStatus();
    }

    public boolean payOrder(int id){
        log.info("Updating status for order: " + id);
        orderRepository.updatePayStatusById("Paid", id);
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
        if(customer == null) {
            return false;
        }
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
    public Order updateDescription(Order incomingOrder, OrderLocation incomingLocation){
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
        try{
            if(incomingOrder.getItems() == null){
                return false;
            }
            else {
                Item item = incomingOrder.getItems().stream().filter(i ->
                        (i != null) &&
                                i.getGroceryItem().getId() == itemId).findFirst().get();
                itemRepository.delete(item);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
        // Send customer a notification email.
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
                                    "How did you like your shopper? Give us a rating!");
                }
            } catch (Exception e) {
                log.error("Failed to send email notification on order status update.");
                e.printStackTrace();
            }
        }
        return orderRepository.save(order);
    }

    /**
     * Deletes an order from the repository
     * @param incomingOrder
     * @return boolean
     */
    public boolean deleteOrder(Order incomingOrder) {

        orderRepository.delete(incomingOrder);
        return true;
    }

    /**
     * Calculates the order cost. At the moment, it's a flat rate 5 per delivery + orderItem * quantity. 
     * If something goes wrong return max amount
     * @param orderId
     * @return order cost as a double
     */
    public long calculateOrderCost (int orderId){
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()){
            return (order.get().getItems().stream()
            .map(item -> item.getQuantity() * item.getGroceryItem().getCost().longValue())
            .reduce(500L, (a,b) -> a+b ));    
        }
        return Long.MAX_VALUE;
    }



    /**
     * Sends an HTTP request to api2 for the stripe checkout page.
     * @param orderId
     * @return returns the response for the request
     * @throws JsonProcessingException
     * @throws IllegalStateException when orderCost is less than $0.5 or when something went wrong with the http request
     */
   public String submitOrder (int orderId) throws JsonProcessingException, IllegalStateException{
        long orderCost = calculateOrderCost(orderId);
        if (orderCost < 50){
            throw new IllegalStateException("Amount is less than 50 cents");
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = new HashMap<>();

        request.put("orderReferenceId", String.valueOf(orderId));         
        request.put("orderAmount", String.valueOf(calculateOrderCost(orderId)));
        request.put("receiptEmail", String.valueOf(orderId));

        HttpEntity<String> httpRequest = new HttpEntity<>(new ObjectMapper().writeValueAsString(request), headers);
        String response = restTemplate.postForObject(URI.create(api2Url+"/checkout-order"), httpRequest, String.class);

        if (response == null){
            throw new IllegalStateException ("Http response was null");
        }

        Map<String, String> responseFormat = new HashMap<>();
        responseFormat.put("link", response);

        return new ObjectMapper().writeValueAsString(responseFormat);
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
        orderRepository.save(order.get());
        try {
            sendNotification(shopper.get().getEmail(),
                    "You have been assigned an order",
                    "Order number: " + orderId + "\n" +
                            "The order is ready to be fulfilled.\n" +
                            "Good luck, and happy shopping!");
        } catch (Exception e) {
            log.error("Failed to send email notification on assigning shopper.");
            e.printStackTrace();
        }
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
        log.info("Sending notification post request to " + notificationApiUrl);
        return restTemplate.postForEntity(notificationApiUrl + "?html={html}", map, Object.class, uriParam);
    }
}