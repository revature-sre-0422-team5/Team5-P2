package com.team5.deliveryApi.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "customerId", nullable = false)
    private int customerId;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "store_location", nullable = false)
    private String store_location;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "item_Id", nullable = false)
    private int[] item_Id;
}
