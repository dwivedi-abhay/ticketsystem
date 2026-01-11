package com.ticketsystem.ticketsystem.booking.services;

import com.ticketsystem.ticketsystem.booking.domain.Booking;
import com.ticketsystem.ticketsystem.booking.domain.BookingSeat;
import com.ticketsystem.ticketsystem.booking.domain.BookingStatus;
import com.ticketsystem.ticketsystem.booking.repositories.BookingRepository;
import com.ticketsystem.ticketsystem.booking.repositories.BookingSeatRepository;
import com.ticketsystem.ticketsystem.booking.workflow.BookingStateMachine;
import com.ticketsystem.ticketsystem.seat.ConfirmedBookedSeat;
import com.ticketsystem.ticketsystem.seat.repository.ConfirmedBookedSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.Instant.now;

@Service
public class BookingService {

    private static int BOOKING_TTL_MINUTES = 10;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ConfirmedBookedSeatRepository confirmedBookedSeatRepository;
    private final SeatLockService seatLockService;

    private final BookingStateMachine bookingStateMachine;


    public BookingService(
            BookingRepository bookingRepository,
            BookingSeatRepository bookingSeatRepository,
            ConfirmedBookedSeatRepository confirmedBookedSeatRepository,
            SeatLockService seatLockService,
            BookingStateMachine bookingStateMachine
    ) {
        this.bookingRepository = bookingRepository;
        this.seatLockService = seatLockService;
        this.bookingSeatRepository = bookingSeatRepository;
        this.bookingStateMachine = bookingStateMachine;
        this.confirmedBookedSeatRepository = confirmedBookedSeatRepository;
    }
    public Booking createBooking(Long userId, Long showId, List<Long> seatIds){
        Booking booking = new Booking(userId, showId, BookingStatus.INITIATED, now(), now().plusSeconds(10*60L));

        bookingRepository.save(booking);
        boolean locked = seatLockService.lockSeats(showId, seatIds, booking.getId());

        if(!locked){
            booking.setStatus(BookingStatus.EXPIRED);
            bookingRepository.save(booking);
            throw new IllegalStateException("Seat already locked");
        }

        bookingSeatRepository.saveAll(seatIds.stream().map(seatId -> new BookingSeat(booking.getId(), seatId)).toList());
        booking.setStatus(BookingStatus.PAYMENT_PENDING);
        return bookingRepository.save(booking);
    }

    @Transactional
    public void confirmBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalStateException("Booking not found"));

        if(!bookingStateMachine.canConfirmBooking(booking)){
            return;
        }

        List<BookingSeat> bookingSeats = bookingSeatRepository.findByBookingId(bookingId);

        for(BookingSeat seat : bookingSeats){
            confirmedBookedSeatRepository.save(new ConfirmedBookedSeat(booking.getShowId(), seat.getSeatId(), bookingId));
        }

        booking.markConfirmed();
        bookingRepository.save(booking);
    }

    public void expireBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->  new IllegalStateException("Booking not found"));

        if(!bookingStateMachine.canExpire(booking)){
            return;
        }

        List<BookingSeat> bookingSeats = bookingSeatRepository.findByBookingId(bookingId);

        seatLockService.releaseSeats(booking.getShowId(), bookingSeats.stream().map(BookingSeat::getSeatId).toList(), bookingId);

        booking.markExpired();
        bookingRepository.save(booking);
    }

    public Booking getBooking(Long bookingId){
        return bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalStateException("Booking not found"));
    }
}
