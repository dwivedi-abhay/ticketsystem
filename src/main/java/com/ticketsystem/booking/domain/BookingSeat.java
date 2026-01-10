package com.ticketsystem.booking.domain;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "booking_seat")
public class BookingSeat {

    public BookingSeat(Long bookingId, Long seatId){
        this.bookingId = bookingId;
        this.seatId = seatId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookingId;

    @Column(nullable = false)
    private Long seatId;


}
