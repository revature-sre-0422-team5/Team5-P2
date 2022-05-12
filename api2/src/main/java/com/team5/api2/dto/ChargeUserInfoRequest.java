package com.team5.api2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeUserInfoRequest {

    private int orderReferenceId;

    private long orderAmount;

    private String receiptEmail;
}
