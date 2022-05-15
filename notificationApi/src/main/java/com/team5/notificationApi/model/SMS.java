package com.team5.notificationApi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SMS {
    private String recipient;
    private String message;

    public SMS() {}

    public SMS(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }
}
