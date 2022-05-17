package com.team5.deliveryApi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team5.deliveryApi.dto.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "orderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "date", nullable = false)
    private String date;

    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus status = OrderStatus.MakingOrder;

    @Column(name = "store_location", nullable = false)
    private String store_location;

    @Column(name = "pay_status")
    private String pay_status = "unpaid";

    @Column(name = "location_description")
    private String description;

    @OneToMany
    @JoinColumn(name = "orderId")
    private List<Item> items;
  
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shopper_id")
    @JsonIgnore
    private Shopper shopper;
}
