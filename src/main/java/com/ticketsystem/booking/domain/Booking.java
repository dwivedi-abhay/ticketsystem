package com.ticketsystem.booking.domain;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long showId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    protected Booking() {
        // JPA only
    }

    public Booking(
            Long userId,
            Long showId,
            BookingStatus status,
            Instant createdAt,
            Instant expiresAt
    ) {
        this.userId = userId;
        this.showId = showId;
        this.status = status;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }


    public void setStatus(BookingStatus status){
        this.status = status;
    }

}
