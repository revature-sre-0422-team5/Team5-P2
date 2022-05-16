package com.team5.deliveryApi.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Shoppers")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Shopper {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "loggedIn", nullable = false)
    private boolean loggedIn;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
    private List<Order> orders;

    /*
    // Need to implement FK on OrderId with the Orders table, so that shopper knows which order to shop for,
    // and see all it's related details like ID, updates status, views destination, and all items in the order.
    @Column(name = "orderId", nullable = false)
    private int orderId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "item_Id", nullable = false)
    private int[] item_Id;
    */
}
