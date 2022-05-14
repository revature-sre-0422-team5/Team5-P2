package com.team5.deliveryApi.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Table (name="OrderItems")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @Column (name ="itemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    @Column (name="itemName")
    private String itemName;

    @Column (name="itemDescription")
    private String itemDescription;

    @Column (name="itemPriceInCents")
    private long itemPriceInCents;

    @Column (name="itemStatus")
    private String itemStatus; //Double check if you can enforce a boolean type

}
