package com.ticketsystem.payments;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false, unique = true)
    private String providerRef;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Instant createdAt;


    public void markSuccess(){
        paymentStatus = PaymentStatus.SUCCESS;
    }

    public void markFailed(){
        paymentStatus = PaymentStatus.FAILED;
    }
}
