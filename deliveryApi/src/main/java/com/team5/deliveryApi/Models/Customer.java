package com.team5.deliveryApi.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Customers")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //This is Customer's name
    @Column(name = "name", nullable = false)
    private String name;


   //This is Customer's login ID
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "location", nullable = false)
    private String location;


    @Column(name = "email")
    private String email;
//Add one to many relation to orders
}
