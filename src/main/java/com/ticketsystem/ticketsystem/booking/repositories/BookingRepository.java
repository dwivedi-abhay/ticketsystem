package com.ticketsystem.ticketsystem.booking.repositories;

import com.ticketsystem.ticketsystem.booking.domain.Booking;
import com.ticketsystem.ticketsystem.booking.domain.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByIdAndUserId(Long id, Long userId);

    boolean existsByIdAndStatus(Long id, BookingStatus status);

}
