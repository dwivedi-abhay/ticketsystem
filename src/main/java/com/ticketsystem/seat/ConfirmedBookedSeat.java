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

    public ConfirmedBookedSeat(Long showId, Long seatId, Long bookingId){
        this.bookingId = bookingId;
        this.showId = showId;
        this.seatId = seatId;
    }

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
