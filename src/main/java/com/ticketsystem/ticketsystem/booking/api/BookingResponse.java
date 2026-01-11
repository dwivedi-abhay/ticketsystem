package com.ticketsystem.ticketsystem.booking.api;

import com.ticketsystem.ticketsystem.booking.domain.BookingStatus;

import java.time.Instant;

public record BookingResponse (
        Long bookingId,
        BookingStatus status,
        Instant expiresAt

){}
