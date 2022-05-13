package com.team5.deliveryApi.controllers;

import com.team5.deliveryApi.models.Order;
import com.team5.deliveryApi.services.OrderService;
import com.team5.deliveryApi.dto.Item;
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

    @PostMapping("/new")
    public String createOrder(@RequestBody Order incomingOrder) {


        log.info("Creating new Order");
        boolean success = orderService.saveOrder(incomingOrder);
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
}