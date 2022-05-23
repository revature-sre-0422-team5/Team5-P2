package com.team5.api2.servicetests;

import com.team5.api2.Repositories.PaymentsRequestRepository;
import com.team5.api2.services.PaymentsServices;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.team5.api2.models.OrderPaymentEntity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PaymentsServicesTest {

    @Mock
    private RestTemplate mockRestTemplate;

    @Mock
    private ResponseEntity<String> mockResponse;

    @Mock
    private SessionCreateParams mockSessionParams;
    
    @Mock
    private Session mockStripeSessionReturn;

    @Mock
    private PaymentsRequestRepository payrepo;

    @InjectMocks
    private PaymentsServices paymentsService;

    @Test
    void chargeUser (){        
        try (MockedStatic<Stripe> mockStripe = Mockito.mockStatic(Stripe.class)){

            try (MockedStatic<Session> mockStripeSession = Mockito.mockStatic(Session.class)) {
                String mockUrl = "http://test-stripe-url.com";

                //The injected mock gets modified by the service method body so have to use any(), can't directly compare from what we set up
                mockStripeSession.when (() -> Session.create(any(SessionCreateParams.class))).thenReturn(mockStripeSessionReturn);

                Mockito.when (mockStripeSessionReturn.getPaymentIntent()).thenReturn("test-stripe-session");

                Mockito.when(mockStripeSessionReturn.getUrl()).thenReturn(mockUrl);

                Assertions.assertEquals(mockUrl,paymentsService.chargeUser(1, 1234L));
            }
        }

        Assertions.assertEquals(null, paymentsService.chargeUser(1,1234L));
    }

    @Test
    void processOrderStatus (){
        try (MockedStatic<Session> mockStripeSession = Mockito.mockStatic(Session.class)) {
            //Http request is ok
            mockStripeSession.when ( () -> Session.retrieve("test-session")).thenReturn (mockStripeSessionReturn);

            String stripePaymentIntentId = "mock-payment-intent";
            OrderPaymentEntity mockOrderPayment = new OrderPaymentEntity(1, stripePaymentIntentId);
            ArrayList<OrderPaymentEntity> mockOrderPaymentsList = new ArrayList<>();            
            mockOrderPaymentsList.add(new OrderPaymentEntity(1, stripePaymentIntentId));

            Mockito.when(payrepo.findByStripeId(stripePaymentIntentId)).thenReturn(mockOrderPaymentsList);
            Mockito.when(mockStripeSessionReturn.getPaymentIntent()).thenReturn(stripePaymentIntentId);

            
            Mockito.when (mockRestTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any()))
            .thenReturn(mockResponse);

            Mockito.when(mockResponse.getStatusCode()).thenReturn(HttpStatus.OK);
            Mockito.when(mockResponse.getBody()).thenReturn("test body");
            

            Assertions.assertEquals ("test body", paymentsService.processOrderStatus("test-session"));
    
            //Http request isn't ok    
            Assertions.assertEquals (null, paymentsService.processOrderStatus("mockSessionId"));
        }
        catch (StripeException e){
            e.printStackTrace();
        }
    }
    
}
