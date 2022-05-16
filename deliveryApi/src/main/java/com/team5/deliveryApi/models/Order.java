package com.team5.deliveryApi.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @Column(name = "orderId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

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

    @Column(name = "pay_status")
    private String pay_status = "unpaid";

    @Column(name = "from_location")
    private String from_location;

    @Column(name = "location_description")
    private String description;


      @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
      private List<Item> items;



}
