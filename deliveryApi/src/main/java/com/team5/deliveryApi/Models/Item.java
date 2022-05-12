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

        @Column(name = "productName", nullable = false)
        private String productName;

        @Enumerated(EnumType.STRING)
        @Column
        private ItemStatus status;
    }

