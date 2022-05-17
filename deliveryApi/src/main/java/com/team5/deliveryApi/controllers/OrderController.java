package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.Item;
import com.team5.deliveryApi.dto.OrderStatus;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.services.OrderService;
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
    

   /* @GetMapping("/status/{id}")
      public ResponseEntity viewStatusById(@PathVariable int id) {
        return orderService.viewStatusById(id);
    }*/

    @PutMapping("/pay/{id}")
    public ResponseEntity payOrderById(@PathVariable int id){
        return ResponseEntity.ok(orderService.payOrder(id));
    }

    @GetMapping("/viewId/{odrId}")
    public ResponseEntity<Order> viewOrderById(@PathVariable int odrId) {
        Order outGoingOrder = orderService.findById(odrId);
        return ResponseEntity.ok().body(outGoingOrder);

    }
<<<<<<< HEAD

    @RequestMapping(value = "/addLocationDescription/{odrId}", method = RequestMethod.PUT)
     public ResponseEntity<Order>  updateLocationOrder(@RequestBody OrderLocation orderLocation, @PathVariable int odrId) {
         Order Out=orderService.updateLocation(orderService.findById(odrId),orderLocation);
         return ResponseEntity.ok().body(Out);
    }


    @RequestMapping(value = "/removeItem/{odrId}/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity removeItem(@PathVariable int odrId,@PathVariable int itemId) {
       log.info( "checking"+orderService.findById(odrId));
       boolean success=orderService.removeItem(orderService.findById(odrId),itemId);
=======
    @PutMapping(value = "/adddescription/{odr_id}")
    public Order updateLocationOrder(@RequestBody OrderLocation orderLocation, @PathVariable int odr_id) {
        Order Out=orderService.updateLocation(orderService.findByOrderId(odr_id),orderLocation);
        return Out;
    }

    @RequestMapping(value = "/removeitem/{odr_id}/{item_id}", method = RequestMethod.PUT)
    public ResponseEntity<String> removeItem(@PathVariable int odrId,@PathVariable int itemId) {
       boolean success=orderService.removeItem(orderService.findByOrderId(odrId),itemId);
>>>>>>> 9510397c28052786f794f9af681f36b54f56972d
       if (success==true) {
           return ResponseEntity.ok().body( "Successfully Removed the item");
       }else{
           return ResponseEntity.ok().body("Error in removing item");
       }


    }
      
  @PostMapping(value = "/addItem/{odrId}/{groceryItemID}")
  public ResponseEntity <Order> addItem(@PathVariable int odrId, @PathVariable int groceryItemID,
                                        @RequestParam("qty") int quantity) {
        log.info("checking"+odrId+groceryItemID+quantity);
        Order order=orderService.addItem(odrId,groceryItemID,quantity);
        log.info("in controller");
        return ResponseEntity.ok().body(order);
   }

<<<<<<< HEAD
   @RequestMapping(value="/submitOrder/{odrId}", method = RequestMethod.PUT)
    public ResponseEntity submitOrder(@PathVariable int odrId){
         boolean success= orderService.submitOrder(orderService.findById(odrId));
        if (success==true) {
            return ResponseEntity.ok().body("Order submitted Successfully");
        }else{
            return ResponseEntity.ok().body("Error in submitting order");
        }
    }
    @DeleteMapping("/delete/{odrId}")
    public ResponseEntity cancelOrder(@PathVariable int odrId) {
         boolean success=orderService.deleteOrder(orderService.findById(odrId));
=======
    /**
     * Updates the status of an order.
     * @param odr_id The ID of the order to update.
     * @param orderStatus The new status to assign to the order.
     * @return The HTTP response containing the newly updated order.
     */
   @PutMapping("/status/{odr_id}")
    public ResponseEntity<Order> changeOrderStatus(@PathVariable int odr_id,
                                                   @RequestParam("status") String orderStatus) {
       OrderStatus status = OrderStatus.valueOf(orderStatus);
       return ResponseEntity.ok(orderService.updateOrderStatus(odr_id, status));
    }

    @DeleteMapping("/delete/{odr_id}")
    public ResponseEntity<String> cancelOrder(@PathVariable int odr_id) {
         boolean success=orderService.deleteOrder(orderService.findByOrderId(odr_id));
>>>>>>> 9510397c28052786f794f9af681f36b54f56972d
         if(success==true) {
             return ResponseEntity.ok().body("Order cancelled Successfully");
         }
         else{
             return ResponseEntity.ok().body("Error in cancelling Order");
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