package com.ticketsystem.seat;
import jakarta.persistence.*;

@Entity
@Table(
        name = "confirmed_booked_seat",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"show_id", "seat_id"})
        }
)
public class ConfirmedBookedSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long showId;

    @Column(nullable = false)
    private Long seatId;

    @Column(nullable = false)
    private Long bookingId;

    // getters only
}
