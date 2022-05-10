package com.team5.entities;

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

    private long itemId;

    @Column (name="itemName")
    private String itemName;

    @Column (name="itemDescription")
    private String itemDescription;

}
