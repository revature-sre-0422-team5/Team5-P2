package com.team5.notificationApi.controller;

import com.team5.notificationApi.model.Mail;
import com.team5.notificationApi.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * The email HTTP request handler of
 * the notification API.
 */
@RestController
@RequestMapping("/notification/email")
@Slf4j
public class EmailController {
    @Autowired
    private EmailService emailService;

    /**
     * Sends a mail message to an email.
     * @param mail The contents of the mail.
     */
    @PostMapping
    public ResponseEntity<Mail> sendMail(@RequestBody Mail mail,
                                         @RequestParam(defaultValue = "false", name = "html") boolean isHtml) {
        try {
            emailService.sendMail(emailService.buildMimeMessage(mail, isHtml));
            mail.setAttachments(new MultipartFile[0]);
            return ResponseEntity.ok(mail);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(mail);
        }
    }

    /**
     * Sends an email with file attachments.
     * @param mail The contents of the mail.
     * @param files The file attachments of the mail.
     * @return The ResponseEntity containing the contents of the mail.
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Mail> sendMailWithAttachments(
            @RequestPart(name = "mail") Mail mail,
            @RequestPart(name = "attachments", required = false) MultipartFile[] files) {
        mail.setAttachments(files);
        return sendMail(mail, false);
    }


}
