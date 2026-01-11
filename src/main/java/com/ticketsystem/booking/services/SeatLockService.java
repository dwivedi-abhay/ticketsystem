package com.ticketsystem.booking.services;

import java.util.List;

public interface SeatLockService {
    boolean lockSeats(Long showId, List<Long> seatIds, Long bookingId);

    void releaseSeats(Long showId, List<Long> seatIds, Long bookingId);

}
