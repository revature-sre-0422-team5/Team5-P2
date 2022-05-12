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
    @Column(name="orderId")
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

    @Column(name = "from_location")
    private String from_location;

    @Column(name = "location_description")
    private String description;


    @Column(name = "item_Id")//nullable=false
     //private int[] item_Id;
     private int item_Id;

    @Column(name = "item")
    private String item;

    @Column(name = "item_description")
    private String item_description;

  /* @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="customerId",referencedColumnName = "id")
    private Customer customer;*/

    ///not really needed because even he orders same for a different day the order id is different



}
