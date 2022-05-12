package com.team5.api2.services;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.team5.api2.Repositories.PaymentsRequestRepository;
import com.team5.api2.models.OrderPaymentEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("PaymentsService")
public class PaymentsServices {

    @Autowired
    private PaymentsRequestRepository payrepo;

    @Autowired
    public PaymentsServices (@Value("${api.stripekey}") String apiKey){
        Stripe.apiKey = apiKey;
    }

    public OrderPaymentEntity chargeUser (int orderReferenceId, long chargeAmount, String email){
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", chargeAmount);
            params.put("currency", "cad");
            params.put("source", "tok_visa");
            params.put("description", ("Order #" + orderReferenceId));
            params.put("receipt_email", email);
            Charge charge = Charge.create(params);

            OrderPaymentEntity ope = new OrderPaymentEntity(orderReferenceId, charge.getId());
            payrepo.save(ope);
            return ope;
        } 
        catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
