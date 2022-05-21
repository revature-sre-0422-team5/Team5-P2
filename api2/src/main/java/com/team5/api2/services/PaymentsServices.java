package com.team5.api2.services;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Payout;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
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

    public String chargeUser (int orderReferenceId, long chargeAmount)
    {
        try {
            SessionCreateParams params =
            SessionCreateParams.builder()
              .setMode(SessionCreateParams.Mode.PAYMENT)
              .setSuccessUrl("http://localhost:8081/success?session_id={CHECKOUT_SESSION_ID}")
              .setCancelUrl("https://example.com/")
              .addLineItem(
              SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(
                  SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("cad")
                    .setUnitAmount(chargeAmount)
                    .setProductData(
                      SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Order #" + orderReferenceId)
                        .build())
                    .build())
                .build())
              .build();
    
          Session session = Session.create(params);
          OrderPaymentEntity ope = new OrderPaymentEntity(orderReferenceId, session.getPaymentIntent());
          payrepo.save(ope);

          return session.getUrl();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void payShopper (String userEmail, long amount){
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount);
            params.put("currency", "cad");
    
            Payout payout = Payout.create(params);

        }
        catch (StripeException e){
            e.printStackTrace();
        }
    }
}
