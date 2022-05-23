package com.team5.api2.services;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Payout;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.team5.api2.Repositories.PaymentsRequestRepository;
import com.team5.api2.models.OrderPaymentEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service("PaymentsService")
@Slf4j
public class PaymentsServices {

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PaymentsRequestRepository payrepo;

    @Value ("${api.deliveryapi}")
    private String deliveryApiUrl;

    @Autowired
    public PaymentsServices (@Value("${api.stripekey}") String apiKey){
        Stripe.apiKey = apiKey;
    }

    public String chargeUser (int orderReferenceId, long chargeAmount)
    {
        try {
            log.info("Creating stripe link for " + orderReferenceId +" amount: " + chargeAmount);

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
            log.error("Something went wrong generating a stripe link for user" + orderReferenceId);
            e.printStackTrace();
            return null;
        }
    }

    public String processOrderStatus (String sessionId) throws StripeException {
        try {
            log.info ("processing order with session: "+ sessionId);

            String paymentId = Session.retrieve(sessionId).getPaymentIntent();

            int orderPaymentId = payrepo.findByStripeId(paymentId).get(0).getOrderPaymentId();
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
    
            HttpEntity<String> entity = null;
    
            log.info("Submitting update payment status for " + orderPaymentId);
    
            ResponseEntity<String> response = restTemplate.exchange(deliveryApiUrl+"/order/pay/"+ orderPaymentId, HttpMethod.PUT, entity, String.class);
    
            if (response.getStatusCode() != HttpStatus.OK){
                throw new IllegalStateException ("Something went wrong with the http request");
            }
    
            return response.getBody();    
        }
        catch (Exception e){
            log.error("Something went wrong for sessionId" + sessionId);
            e.printStackTrace();
            return null;
        }
    }
}
