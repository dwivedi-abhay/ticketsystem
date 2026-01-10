package com.ticketsystem.booking.repositories;

import com.ticketsystem.booking.domain.Booking;
import com.ticketsystem.booking.domain.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
    List<BookingSeat> findByBookingId(Long bookingId);
    void deleteByBookingId(Long bookingId);
}
