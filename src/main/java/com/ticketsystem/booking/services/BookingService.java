package com.ticketsystem.booking.services;

import com.ticketsystem.booking.domain.Booking;
import com.ticketsystem.booking.domain.BookingSeat;
import com.ticketsystem.booking.domain.BookingStatus;
import com.ticketsystem.booking.repositories.BookingRepository;
import com.ticketsystem.booking.repositories.BookingSeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.Instant.now;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final SeatLockService seatLockService;

    private final BookingSeatRepository bookingSeatRepository;

    public BookingService(
            BookingRepository bookingRepository,
            SeatLockService seatLockService,
            BookingSeatRepository bookingSeatRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.seatLockService = seatLockService;
        this.bookingSeatRepository = bookingSeatRepository;
    }
    public Booking createBooking(Long userId, Long showId, List<Long> seatIds){
        Booking booking = new Booking(userId, showId, BookingStatus.INITIATED, now(), now().plusSeconds(10*60));

        bookingRepository.save(booking);
        boolean locked = seatLockService.lockSeats(showId, seatIds, booking.getId());

        if(!locked){
            booking.setStatus(BookingStatus.EXPIRED);
            bookingRepository.save(booking);
            return null;

        }

        bookingSeatRepository.saveAll(seatIds.stream().map(seatId -> new BookingSeat(booking.getId(), seatId)).toList());
        booking.setStatus(BookingStatus.PAYMENT_PENDING);
        return bookingRepository.save(booking);

    }
}
