package com.ticketsystem.scheduler.booking;


import com.ticketsystem.booking.domain.Booking;
import com.ticketsystem.booking.domain.BookingStatus;
import com.ticketsystem.booking.repositories.BookingRepository;
import com.ticketsystem.booking.repositories.BookingSeatRepository;
import com.ticketsystem.booking.services.SeatLockService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class BookingExpiryScheduler {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final SeatLockService seatLockService;

    public BookingExpiryScheduler(BookingRepository bookingRepository, BookingSeatRepository bookingSeatRepository, SeatLockService seatLockService){
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.seatLockService = seatLockService;
    }

    @Scheduled(fixedDelay = 30_000)
    public void expireBookings(){

        List<Booking> expiredBookings = bookingRepository.findByStatusInAndExpiresAtBefore(
                List.of(
                        BookingStatus.INITIATED,
                        BookingStatus.PAYMENT_PENDING
                ),
                Instant.now()
        );

        for(Booking booking : expiredBookings){
            List<Long> seatIds = bookingSeatRepository.findByBookingId(booking.getId()).stream().map(seat -> seat.getSeatId()).toList();

            seatLockService.releaseSeats(booking.getShowId(), seatIds, booking.getId());
            booking.markExpired();
            bookingRepository.save(booking);
        }
    }


}
