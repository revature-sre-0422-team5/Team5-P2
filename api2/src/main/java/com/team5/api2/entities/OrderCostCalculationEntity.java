package com.team5.api2.entities;

import javax.persistence.Entity;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCostCalculationEntity {
    private int orderCost;    
}
