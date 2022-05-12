package com.team5.deliveryApi.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Items")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "grocery_item_id")
    private GroceryItem groceryItem;
}

