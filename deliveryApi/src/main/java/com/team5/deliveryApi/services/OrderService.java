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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class OrderService {

    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private GroceryItemRepository groceryItemRepository;
    private ItemRepository itemRepository;
<<<<<<< HEAD
    Logger logger = LoggerFactory.getLogger(OrderService.class);
    public OrderService(OrderRepository orderRepository,GroceryItemRepository groceryItemRepository,ItemRepository itemRepository){
        super();
        this.orderRepository = orderRepository;
        this.groceryItemRepository= groceryItemRepository;
        this.itemRepository=itemRepository;
    }
=======
    private ShopperRepository shopperRepository;
>>>>>>> 9510397c28052786f794f9af681f36b54f56972d

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


<<<<<<< HEAD

 /* public ResponseEntity viewStatusById(int id){
        return ResponseEntity.ok(orderRepository.findById(id).getStatus());
    }*/
=======
    public ResponseEntity viewStatusById(int id){
        return ResponseEntity.ok(orderRepository.findById(id).get().getStatus());
    }
>>>>>>> 9510397c28052786f794f9af681f36b54f56972d

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
<<<<<<< HEAD
    public Order viewOrderById(int id){
        Order outGoingOrder=(orderRepository.findById(id).get());
        return outGoingOrder;
    }
   public Order findById(int odrId) {
        logger.info("Getting Order by Id");
        //List<Item> outGoingItem =itemRepository.findById(odrId).get();
        Order outGoingOrder = orderRepository.findById(odrId).get();
        return outGoingOrder;

=======

    public Order viewOrderById(int id) {
        Order outGoingOrder = orderRepository.findById(id).get();
        return outGoingOrder;
    }
    public Order findByOrderId(int odrId) {
        Order outGoingOrder = orderRepository.findById(odrId).get();

        if (outGoingOrder != null) {

            return outGoingOrder;
        } else { return null;
        }
>>>>>>> 9510397c28052786f794f9af681f36b54f56972d
    }

    public Order updateLocation(Order incomingOrder, OrderLocation incomingLocation){
        incomingOrder.getCustomer().setLocation(incomingLocation.getDto_from_location());
        incomingOrder.setDescription(incomingLocation.getDto_description());
        Order updatedOrder=orderRepository.save(incomingOrder);
        return updatedOrder;
    }


    public boolean removeItem(Order incomingOrder,int itemId){

      Item item = incomingOrder.getItems().stream().filter(i -> i.getGroceryItem().getId() == itemId).findFirst().get();
            //Item items=itemRepository.delete(item);
        log.info("checking"+item+incomingOrder);
            itemRepository.delete(item);
          //List<Item> items=itemRepository.removeItem(itemId);
            itemRepository.save(item);
            orderRepository.save(incomingOrder);
        log.info("checking"+item+incomingOrder);
            return true;

    }

    public Order addItem(int odrID,int gItemID,int qnty) {
          log.info("checking"+odrID+gItemID+qnty);
          GroceryItem groceryItem=groceryItemRepository.findById(gItemID).get();
          log.info("Checking Grocery Item"+groceryItem);
<<<<<<< HEAD
          Item item =new Item(qnty, ItemStatus.Added,groceryItem,orderRepository.findById(odrID).get());
          logger.info("item"+item);
          Order addedOrder=orderRepository.findById(odrID).get();
=======
          Item item =new Item(qnty, ItemStatus.Added,groceryItem);
          Order addedOrder = orderRepository.findById(odrID).get();
>>>>>>> 9510397c28052786f794f9af681f36b54f56972d
          addedOrder.getItems().add(item);
          log.info("Added Order"+addedOrder);
          orderRepository.save(addedOrder);
          log.info("Added Order after saving"+addedOrder);
          return (addedOrder);
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