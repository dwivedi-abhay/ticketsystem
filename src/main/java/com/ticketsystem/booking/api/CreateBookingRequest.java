package com.ticketsystem.booking.api;

import java.util.List;

public record CreateBookingRequest(
        Long showId,
        List<Long> seatIds
){}
