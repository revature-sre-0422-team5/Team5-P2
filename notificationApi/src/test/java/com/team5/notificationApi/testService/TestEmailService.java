package com.team5.notificationApi.testService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.notificationApi.model.Mail;
import com.team5.notificationApi.service.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

@SpringBootTest
public class TestEmailService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Tests whether the email service creates
     * and returns the email message.
     */
    @Test
    public void shouldReturnEmailMessage() throws Exception {
        Mail mail = new Mail();
        mail.setMessage("Test Message");
        mail.setRecipient("test-recevier@nonexistantwebsite123.com");
        mail.setSubject("Test Subject");
        MimeMessage msg = emailService.buildMimeMessage(mail, false);
        Assertions.assertEquals(msg.getSubject(), mail.getSubject(),
                "Did not return the correct email subject line.");
    }
}
