package com.team5.deliveryApi.models;
import com.team5.deliveryApi.dto.ItemStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int quantity;
    @Enumerated(EnumType.STRING)
    @Column
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "grocery_item_id")
    private GroceryItem groceryItem;


    public Item(int quantity,ItemStatus status,GroceryItem groceryItem){
        this.quantity=quantity;
        this.status=status;
        this.groceryItem=groceryItem;
    }
}

