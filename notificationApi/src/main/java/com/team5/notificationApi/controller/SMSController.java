package com.team5.notificationApi.controller;

import com.team5.notificationApi.model.SMS;
import com.team5.notificationApi.service.SMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The SMS HTTP request handler
 * for the notification API.
 */
@RestController
@RequestMapping("/notification/sms")
@Slf4j
public class SMSController {

    @Autowired
    private SMSService smsService;

    /**
     * Sends an SMS message to a recipient.
     * @param sms The SMS message information.
     * @return The ResponseEntity containing the SMS message.
     */
    @PostMapping
    public ResponseEntity<SMS> sendSMS(@RequestBody SMS sms) {
        try {
            smsService.sendSMS(smsService.buildSMS(sms));
            return ResponseEntity.ok(sms);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(sms);
        }
    }

    /**
     * Logs the delivery status of an SMS message callback.
     * @param map The callback status of the SMS.
     * @return The ResponseEntity containing the delivery status of the SMS.
     */
    @PostMapping(value = "/status")
    public ResponseEntity<String> deliveryStatus(@RequestBody Map<String, String> map) {
        log.info("[POST] SMS delivery status for " + map.get("From") + " is " + map.get("SmsStatus") + ".");
        return ResponseEntity.ok(map.get("SmsStatus"));
    }
}
