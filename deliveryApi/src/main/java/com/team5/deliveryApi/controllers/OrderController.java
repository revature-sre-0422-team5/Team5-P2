package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.Item;
import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.services.OrderService;
import com.team5.deliveryApi.dto.OrderLocation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/all")
    public ResponseEntity viewAllOrders(){
        return orderService.viewAllOrders();
    }
    

    @GetMapping("/status/{id}")
    public ResponseEntity viewStatusById(@PathVariable int id) {
        return orderService.viewStatusById(id);
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity payOrderById(@PathVariable int id){
        return ResponseEntity.ok(orderService.payOrder(id));
    }




    @PostMapping("/new")
    public ResponseEntity createOrder(@RequestBody Order incomingOrder) {

        log.info("Creating new Order");
        boolean success = orderService.saveOrder(incomingOrder);
        log.info("incoming order" + incomingOrder);
        if (success == true) {
            return ResponseEntity.ok().body("Successfully created new order");
        } else {
            return ResponseEntity.ok().body("Error in creating new Order");
        }
    }

    @GetMapping("/viewId/{odrId}")
    public ResponseEntity<Order> viewOrderById(@PathVariable int odrId) {
        Order outGoingOrder = orderService.findByOrderId(odrId);
        return ResponseEntity.ok().body(outGoingOrder);

    }

    @RequestMapping(value = "/addLocationDescription/{odrId}", method = RequestMethod.PUT)
     public ResponseEntity<Order>  updateLocationOrder(@RequestBody OrderLocation orderLocation, @PathVariable int odrId) {
         Order Out=orderService.updateLocation(orderService.findByOrderId(odrId),orderLocation);
         return ResponseEntity.ok().body(Out);
    }


    @RequestMapping(value = "/removeItem/{odrId}/{itemId}", method = RequestMethod.PUT)
    public ResponseEntity removeItem(@PathVariable int odrId,@PathVariable int itemId) {

       boolean success=orderService.removeItem(orderService.findByOrderId(odrId),itemId);
       if (success==true) {
           return ResponseEntity.ok().body( "Successfully Removed the item");
       }else{
           return ResponseEntity.ok().body("Error in removing item");
       }


    }
   @RequestMapping(value = "/addItem/{odrId}/{groceryItemID}", method = RequestMethod.POST)
  public ResponseEntity <Order> addItem(@PathVariable int odrId, @PathVariable int groceryItemID, @RequestParam("qty") int quantity) {
        log.info("checking"+odrId+groceryItemID+quantity);
       Order order=orderService.addItem(odrId,groceryItemID,quantity);
       log.info("in controller");
       return ResponseEntity.ok().body(order);

   }

   @RequestMapping(value="/submitOrder/{odrId}", method = RequestMethod.PUT)
    public ResponseEntity submitOrder(@PathVariable int odrId){
         boolean success= orderService.submitOrder(orderService.findByOrderId(odrId));
        if (success==true) {
            return ResponseEntity.ok().body("Order submitted Successfully");
        }else{
            return ResponseEntity.ok().body("Error in submitting order");
        }
    }
    @DeleteMapping("/delete/{odrId}")
    public ResponseEntity cancelOrder(@PathVariable int odrId) {
         boolean success=orderService.deleteOrder(orderService.findByOrderId(odrId));
         if(success==true) {
             return ResponseEntity.ok().body("Order cancelled Successfully");
         }
         else{
             return ResponseEntity.ok().body("Error in cancelling Order");
         }
    }
}