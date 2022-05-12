package com.team5.deliveryApi.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemAdd {
    private String itemName;

    private String itemDescription;

    private long itemPriceInCents;
}
