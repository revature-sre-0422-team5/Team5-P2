package com.team5.notificationApi.service;

import com.team5.notificationApi.model.SMS;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * The SMS creator and sending service
 * of the notification API.
 */
@Service
@Slf4j
public class SMSService {
    @Value("${TWILIO_ACCOUNT_SID:none}")
    private String accountSID;

    @Value("${TWILIO_AUTH_TOKEN:none}")
    private String authToken;

    @Value("${TWILIO_PHONE_NUMBER:none}")
    private String phoneNumber;

    @Value("${SERVER_ADDRESS:none}")
    private String serverAddress;

    /**
     * Builds an SMS message with the specified information.
     * @param sms The SMS message to build.
     * @return The created SMS message.
     */
    public MessageCreator buildSMS(SMS sms) {
        Twilio.init(accountSID, authToken);
        MessageCreator messageCreator = Message.creator(new PhoneNumber(sms.getRecipient()),
                new PhoneNumber(phoneNumber), sms.getMessage());
        log.info("[POST] New SMS message has been created with recipient: " + phoneNumber + ".");
        return messageCreator;
    }

    /**
     * Sends the created message to the recipient number with
     * a status callback if server address is available.
     * @param messageCreator The Twilio message containing the message information.
     * @return The message that was sent to the recipient.
     */
    public Message sendSMS(MessageCreator messageCreator) {
        String initialUri = "http://" + serverAddress + ":8080/notification/sms/status";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(initialUri);
        String modifiedUri = builder.host(serverAddress).port("8080").toUriString();
        if (serverAddress.equals("none")) {
            return messageCreator.create();
        }
        return messageCreator.setStatusCallback(
                URI.create(modifiedUri))
                .create();
    }
}
