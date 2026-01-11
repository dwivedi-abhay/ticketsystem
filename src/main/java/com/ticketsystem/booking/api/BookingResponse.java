package com.ticketsystem.booking.api;

import com.ticketsystem.booking.domain.BookingStatus;

import java.time.Instant;

public record BookingResponse (
        Long bookingId,
        BookingStatus status,
        Instant expiresAt

){}
