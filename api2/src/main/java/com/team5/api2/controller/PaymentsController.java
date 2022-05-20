package com.team5.api2.controller;

import com.team5.api2.dto.ChargeUserInfoRequest;
import com.team5.api2.dto.OrderCostCalculationResponse;
import com.team5.api2.dto.OrderCostRequest;
import com.team5.api2.models.OrderPaymentEntity;
import com.team5.api2.services.PaymentsServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentsController {

    @Autowired
    private PaymentsServices ps;

    @GetMapping ("/get-order-cost")
    public ResponseEntity <OrderCostCalculationResponse> getJourneyCost (@RequestBody OrderCostRequest ocr){
        try {
            return ResponseEntity.ok().body(new OrderCostCalculationResponse(1234));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping ("/checkout-order")
    public ResponseEntity<String> chargeUser(@RequestBody ChargeUserInfoRequest cuir){
        try {
            String res = ps.chargeUser(cuir.getOrderReferenceId(), cuir.getOrderAmount());
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping ("/confirm-order/{SESSION_ID}")
    public ResponseEntity<String> chargeUser(@PathVariable String sessionId){
        try {
            return ResponseEntity.ok().body(sessionId);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/pay-shopper")
    public ResponseEntity payShopper(){
        return ResponseEntity.internalServerError().body(null);
    }
    
}
