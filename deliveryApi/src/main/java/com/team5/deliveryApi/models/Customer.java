package com.team5.deliveryApi.models;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

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

    @Column(name= "email_subscribe", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean email_subscribe;

    @Column(name = "email")
    private String email;

 //   @Column(name = "isLoggedIn")
 //   private int isLoggedIn;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
    private List<Order> orders;



}
