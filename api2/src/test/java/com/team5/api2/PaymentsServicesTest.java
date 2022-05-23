package com.team5.api2;

import com.team5.api2.Repositories.PaymentsRequestRepository;
import com.team5.api2.services.PaymentsServices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentsServicesTest {

    @Mock
    private PaymentsRequestRepository payrepo;
    
    @InjectMocks
    private PaymentsServices paymentsService;

    @Test
    void placeHolder (){
        String test = "a";
        String test2 = "a";
        Assertions.assertEquals(test, test2);
    }

    @Test
    void payShopperSkeleton (){
        paymentsService.payShopper("test@example.com", 1234L);
    }
    
}
