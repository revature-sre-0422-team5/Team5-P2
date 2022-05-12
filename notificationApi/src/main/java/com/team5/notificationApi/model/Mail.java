package com.team5.notificationApi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class Mail {
    private String recipient;
    private String subject;
    private String message;
    private String[] cc = {};
    private String[] Bcc = {};
    private MultipartFile[] attachments = {};
}
