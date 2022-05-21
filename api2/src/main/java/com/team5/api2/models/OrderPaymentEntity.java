package com.team5.api2.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "OrderPayments")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentEntity {

    @Id
    @Column(name="orderPaymentId")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int orderPaymentId;

    @Column(name = "stripeChargeId", nullable = false)
    private String stripeChargeId;
    //TODO: Check if this needs a relationship

}
