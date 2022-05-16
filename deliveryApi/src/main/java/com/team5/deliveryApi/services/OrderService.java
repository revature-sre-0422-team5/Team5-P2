package com.team5.deliveryApi.services;

import com.team5.deliveryApi.dto.ItemStatus;
import com.team5.deliveryApi.models.GroceryItem;
import com.team5.deliveryApi.models.Item;
import com.team5.deliveryApi.models.ItemNotFoundException;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.dto.OrderLocation;
import com.team5.deliveryApi.repositories.GroceryItemRepository;
import com.team5.deliveryApi.repositories.ItemRepository;
import com.team5.deliveryApi.repositories.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class OrderService {

    private OrderRepository orderRepository;
    private GroceryItemRepository groceryItemRepository;
    private ItemRepository itemRepository;
    Logger logger = LoggerFactory.getLogger(OrderService.class);
    public OrderService(OrderRepository orderRepository,GroceryItemRepository groceryItemRepository,ItemRepository itemRepository){
        super();
        this.orderRepository = orderRepository;
        this.groceryItemRepository= groceryItemRepository;
        this.itemRepository=itemRepository;
    }



    public ResponseEntity viewAllOrders(){
        return ResponseEntity.ok(orderRepository.findAll());
    }



 /* public ResponseEntity viewStatusById(int id){
        return ResponseEntity.ok(orderRepository.findById(id).getStatus());
    }*/

    public boolean payOrder(int id){
        orderRepository.updatePayStatusById("Paid",id);
        return true;
    }






    public boolean saveOrder(Order incomingOrder) {
        orderRepository.save(incomingOrder);
        return true;
    }
    public Order viewOrderById(int id){
        Order outGoingOrder=(orderRepository.findById(id).get());
        return outGoingOrder;
    }
   public Order findById(int odrId) {
        logger.info("Getting Order by Id");
        //List<Item> outGoingItem =itemRepository.findById(odrId).get();
        Order outGoingOrder = orderRepository.findById(odrId).get();
        return outGoingOrder;

    }

    public Order updateLocation(Order incomingOrder, OrderLocation incomingLocation){

        incomingOrder.setFrom_location(incomingLocation.getDto_from_location());
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
          Item item =new Item(qnty, ItemStatus.Added,groceryItem,orderRepository.findById(odrID).get());
          logger.info("item"+item);
          Order addedOrder=orderRepository.findById(odrID).get();
          addedOrder.getItems().add(item);
          log.info("Added Order"+addedOrder);
          orderRepository.save(addedOrder);
          log.info("Added Order after saving"+addedOrder);
          return (addedOrder);
    }


    public boolean submitOrder(Order incomingOrder) {
        incomingOrder.setStatus("Submitted");
        Order updatedOrder=orderRepository.save(incomingOrder);
        return true;
    }
    public boolean deleteOrder(Order incomingOrder) {

        orderRepository.delete(incomingOrder);
        return true;
    }
}