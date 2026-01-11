package com.ticketsystem.ticketsystem.seat.repository;

import com.ticketsystem.ticketsystem.seat.ConfirmedBookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmedBookedSeatRepository extends JpaRepository<ConfirmedBookedSeat, Long> {
    boolean existsByShowIdAndSeatId(Long showId, Long seatId);
}
