package com.team5.deliveryApi.Models;

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

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Item> items;
    //Add one to many relation to items

  /* @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name="customerId",referencedColumnName = "id")
  private Customer customer;*///not really needed because even he orders same for a different day the order id is different



}
