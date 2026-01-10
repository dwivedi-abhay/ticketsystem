package com.ticketsystem.seat.repository;

import com.ticketsystem.seat.ConfirmedBookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmedBookedSeatRepository extends JpaRepository<ConfirmedBookedSeat, Long> {
    boolean existsByShowIdAndSeatId(Long showId, Long seatId);
}
