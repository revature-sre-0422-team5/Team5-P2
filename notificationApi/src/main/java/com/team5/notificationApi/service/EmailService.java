package com.team5.notificationApi.service;

import com.team5.notificationApi.entity.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an email to the specified email account.
     * @param mail The mail object representing the contents of the email.
     * @return The mail object that was sent to the specified email address.
     */
    public MimeMessage sendMail(Mail mail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, mail.getAttachments().length > 0);

        helper.setReplyTo("noreply@email-api.com");
        helper.setFrom("noreply@email-api.com", "noreply@email-api.com");
        helper.setTo(mail.getRecipient());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getMessage());
        helper.setCc(mail.getCc());
        helper.setBcc(mail.getBcc());
        for (MultipartFile f : mail.getAttachments()) {
            if (f.getOriginalFilename() != null) {
                helper.addAttachment(f.getOriginalFilename(), f);
            } else {
                helper.addAttachment("attachment", f);
            }
        }
        mailSender.send(msg);
        log.info("[POST] Email has been sent out to " + mail.getRecipient() + ".");
        return msg;
    }
}
