package com.team5.deliveryApi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customers")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name= "email_subscribe", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean email_subscribe;

    @Column(name = "email")
    private String email;




    @Column(name = "isloggedin",nullable = false)
    private int isloggedin;


    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Order> orders;


    public Customer(int id, String name, String username, String password, String location, boolean email_subscribe, String email, ArrayList<Order> orders) {
    }
}
