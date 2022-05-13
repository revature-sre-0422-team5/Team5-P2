package com.team5.deliveryApi.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemUpdate {
    
    private int orderItemId;

    private String orderStatus;  
    
}
