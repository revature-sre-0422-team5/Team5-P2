package com.team5.api2.controller;

import com.team5.api2.dto.OrderCostRequest;
import com.team5.api2.entities.OrderCostCalculation;
import com.team5.api2.services.PaymentsServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentsController {

    @Autowired
    private PaymentsServices ps;

    @GetMapping ("/getOrderCost")
    public ResponseEntity <OrderCostCalculation> getJourneyCost (@RequestBody OrderCostRequest ocr){
        try {
            return ResponseEntity.ok().body(new OrderCostCalculation(1234));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
}
