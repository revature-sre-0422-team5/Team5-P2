package com.team5.notificationApi.service;

import com.team5.notificationApi.model.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The email creator and sending service
 * of the notification API.
 */
@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Builds the MimeMessage object with the specified contents.
     * @param mail The contents of the email.
     * @return The built MimeMessage object.
     */
    public MimeMessage buildMimeMessage(Mail mail, boolean isHtml) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, mail.getAttachments().length > 0);

        helper.setReplyTo("noreply@email-api.com");
        helper.setFrom("noreply@email-api.com", "noreply@email-api.com");
        helper.setTo(mail.getRecipient());
        helper.setSubject(mail.getSubject());
        if (isHtml) {
            msg.setText(mail.getMessage(), "UTF-8", "html");
        } else {
            helper.setText(mail.getMessage());
        }
        helper.setCc(mail.getCc());
        helper.setBcc(mail.getBcc());
        for (MultipartFile f : mail.getAttachments()) {
            if (f.getOriginalFilename() != null) {
                helper.addAttachment(f.getOriginalFilename(), f);
            } else {
                helper.addAttachment("attachment", f);
            }
        }
        return msg;
    }

    /**
     * Sends an email to the specified email accounts.
     * @param msg The MimeMessage containing the contents of the mail.
     * @return The MimeMessage to be sent out.
     */
    public MimeMessage sendMail(MimeMessage msg) throws MessagingException {
        mailSender.send(msg);
        // Log the email recipients
        List<Address> addressList = new ArrayList<>(Arrays.asList(msg.getRecipients(Message.RecipientType.TO)));
        addressList.addAll(Arrays.asList(Optional.ofNullable(
                msg.getRecipients(Message.RecipientType.CC)).orElse(new Address[0])));
        addressList.addAll(Arrays.asList(Optional.ofNullable(
                msg.getRecipients(Message.RecipientType.BCC)).orElse(new Address[0])));
        for (Address address : addressList) {
            log.info("[POST] Email has been sent out to " + address.toString() + ".");
        }
        return msg;
    }
}
