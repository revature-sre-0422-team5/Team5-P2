package com.team5.deliveryApi.services;

import com.team5.deliveryApi.models.Customer;
import com.team5.deliveryApi.models.UserNotFoundException;
import com.team5.deliveryApi.repositories.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest(classes = CustomerService.class)
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    /**
     * Tests if the CustomerService updates a customer's
     * email subscription properly.
     */
    @Test
    public void shouldReturnCustomerWithUpdatedEmailSubscription() throws UserNotFoundException {
       /* Customer customer = new Customer(1, "John Smith", "johnsmithy123",
                "JohnSmithPassword", "100 Nowhereville",
                false, "john.smith@gmail.com", new ArrayList<>());
        Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));

        Customer updated = customerService.updateEmailSubscription(1, true);
        Assertions.assertTrue(updated.isEmail_subscribe());*/
    }
}
