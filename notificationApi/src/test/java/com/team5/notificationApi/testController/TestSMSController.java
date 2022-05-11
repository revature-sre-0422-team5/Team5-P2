package com.team5.notificationApi.testController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.notificationApi.service.SMSService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestSMSController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SMSService smsService;

    @Autowired
    private ObjectMapper mapper;

    /**
     * Tests whether the SMS controller returns an OK response.
     */
    @Test
    public void shouldReturnOkStatusSMSMessage() throws Exception {
        MessageCreator messageCreator = Message.creator(new PhoneNumber("+1234567891"),
                new PhoneNumber("+12223334444"), "Test SMS message.");
        Message message = Mockito.mock(Message.class);
        Mockito.when(smsService.sendSMS(Mockito.any())).thenReturn(message);

        Map<String, String> sms = new HashMap<>();
        sms.put("recipient", "+1234567891");
        sms.put("message", "Test SMS");

        mockMvc.perform(MockMvcRequestBuilders.post("/notification/sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sms)))
                .andExpect(status().isOk());
    }

    /**
     * Tests the SMS controller sending a bad request properly
     * if the service throws an exception.
     * @throws Exception
     */
    @Test
    public void shouldSendSMSBadRequest() throws Exception {
        Mockito.when(smsService.sendSMS(Mockito.any())).thenThrow(new ArrayIndexOutOfBoundsException());

        Map<String, String> sms = new HashMap<>();
        sms.put("recipient", "+1234567891");
        sms.put("message", "Test SMS");

        mockMvc.perform(MockMvcRequestBuilders.post("/notification/sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sms)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests whether the delivery status returns an OK response.
     */
    @Test
    public void shouldReturnOkStatusSMSDelivery() throws Exception {
        Map<String, String> sms = new HashMap<>();
        sms.put("SmsStatus", "delivered");
        sms.put("From", "+1234567891");
        mockMvc.perform(MockMvcRequestBuilders.post("/notification/sms/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sms)))
                .andExpect(status().isOk());
    }
}
