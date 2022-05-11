package com.team5.deliveryApi.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Shopper")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Shopper {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "orderId", nullable = false)
    private int orderId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "item_Id", nullable = false)
    private int[] item_Id;
}
