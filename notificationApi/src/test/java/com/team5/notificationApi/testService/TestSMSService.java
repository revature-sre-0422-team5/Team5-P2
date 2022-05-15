package com.team5.notificationApi.testService;

import com.team5.notificationApi.model.SMS;
import com.team5.notificationApi.service.SMSService;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSMSService {
    @Autowired
    private SMSService smsService;

    /**
     * Tests whether the SMS service builds a
     * MessageCreator when sent an SMS object.
     */
    @Test
    public void shouldBuildMessageCreator() {
        SMS sms = new SMS("+1234567891", "This is a test message.");
        MessageCreator msgCreator = smsService.buildSMS(sms);
        Assertions.assertNotNull(msgCreator);
    }

    /**
     * Tests whether the SMS service throws an
     * exception when we try to send an SMS message
     * without any Twilio credentials.
     */
    @Test
    public void shouldThrowExceptionOnSend() {
        SMS sms = new SMS("+1234567891", "This is a test message.");
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            MessageCreator msgCreator = smsService.buildSMS(sms);
            smsService.sendSMS(msgCreator);
        });
    }
}
