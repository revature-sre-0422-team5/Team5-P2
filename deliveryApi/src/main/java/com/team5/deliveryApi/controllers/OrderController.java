package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.services.OrderService;
import com.team5.deliveryApi.dto.Item;
import com.team5.deliveryApi.dto.OrderLocation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Value("${api.directions}")
    private String directionsApiUrl;

    @PostMapping("/new/{customerId}")
    public String createOrder(@RequestBody Order incomingOrder,
                              @PathVariable int customerId) {


        log.info("Creating new Order");
        boolean success = orderService.saveOrder(customerId, incomingOrder);
        log.info("incoming order" + incomingOrder);
        if (success == true) {
            return "Successfully created new order";
        } else {
            return "Error in creating new Order";
        }
    }

    @GetMapping("/all")
    public ResponseEntity viewAllOrders(){
        return orderService.viewAllOrders();
    }
    
    @GetMapping("/viewId/{odr_id}")
    public Order viewRbById(@PathVariable int odr_id) {
        Order outGoingOrder = orderService.findByOrderId(odr_id);
        return outGoingOrder;
    }


    @GetMapping("/status/{id}")
    public ResponseEntity viewStatusById(@PathVariable int id) {
        return orderService.viewStatusById(id);
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity payOrderById(@PathVariable int id){
        return ResponseEntity.ok(orderService.payOrder(id));
    }


     @RequestMapping(value = "/addLocationDescription/{odr_id}", method = RequestMethod.PUT)
     public Order updateLocationOrder(@RequestBody OrderLocation orderLocation, @PathVariable int odr_id) {
         Order Out=orderService.updateLocation(orderService.findByOrderId(odr_id),orderLocation);
         return Out;
    }


    @RequestMapping(value = "/removeItem/{odr_id}/{item_id}", method = RequestMethod.PUT)
    public String removeItem(@PathVariable int odr_id,@PathVariable int item_id) {

       boolean success=orderService.removeItem(orderService.findByOrderId(odr_id),item_id);
       if (success==true) {
           return "Successfully Removed the item";
       }else{
           return "Error in removing item";
       }


    }
   @RequestMapping(value = "/addItem/{odr_id}/{item}", method = RequestMethod.PUT)
   public Order addItem(@PathVariable int odr_id, @PathVariable  int item, @RequestBody Item dto_item) {
       Order Out=orderService.addItem(orderService.findByOrderId(odr_id),item,dto_item);
       return Out;

   }

   @RequestMapping(value="/submitOrder/{odr_id}", method = RequestMethod.PUT)
    public String submitOrder(@PathVariable int odr_id){
         boolean success= orderService.submitOrder(orderService.findByOrderId(odr_id));
        if (success==true) {
            return "Order submitted Successfully";
        }else{
            return "Error in submitting order";
        }
    }
    @DeleteMapping("/delete/{odr_id}")
    public String cancelOrder(@PathVariable int odr_id) {
         boolean success=orderService.deleteOrder(orderService.findByOrderId(odr_id));
         if(success==true) {
             return "Order cancelled Successfully";
         }
         else{
             return "Error in cancelling Order";
         }
    }

    /**
     * Assigns a shopper to an order.
     * @param orderId The ID of the order.
     * @param shopperId The ID of the shopper.
     * @return The HTTP response containing the newly updated order.
     */
    @PutMapping("/assign/{orderId}/{shopperId}")
    public ResponseEntity<Order> assignShopperToOrder(@PathVariable int orderId,
                                                      @PathVariable int shopperId) {
        return ResponseEntity.ok(orderService.assignShopper(orderId, shopperId));
    }

    /**
     * Get directions from the store to the
     * customer's location.
     * @param orderId The order ID containing the address of the customer and store.
     * @return The HTTP response of the directions API.
     */
    @GetMapping("/directions/{orderId}")
    public ResponseEntity<Object> getCustomerDirections(@PathVariable int orderId) {
        Order order = orderService.findByOrderId(orderId);
        Map<String, Object> map = new HashMap<>();
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        map.put("locationFrom", order.getStore_location());
        map.put("locationTo", order.getCustomer().getLocation());
        return restTemplate.postForEntity(directionsApiUrl, map, Object.class);
    }
}