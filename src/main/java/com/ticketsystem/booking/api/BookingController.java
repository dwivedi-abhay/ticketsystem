package com.ticketsystem.booking.api;


import com.ticketsystem.booking.domain.Booking;
import com.ticketsystem.booking.services.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponse createBooking(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody CreateBookingRequest request
    ){
        Booking booking = bookingService.createBooking(userId, request.showId(), request.seatIds());

        return toResponse(booking);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse getBooking(@PathVariable Long bookingId){
        Booking booking = bookingService.getBooking(bookingId);
        return toResponse(booking);
    }

    @PostMapping("/{bookingId}/confirm")
    public void confirmBooking(@PathVariable Long bookingId){
        bookingService.confirmBooking(bookingId);
    }

    @PostMapping("/{bookingId}/expire")
    public void expireBooking(@PathVariable Long bookingId){
        bookingService.expireBooking((bookingId));
    }


    private BookingResponse toResponse(Booking booking){
        return new BookingResponse(
                booking.getId(),
                booking.getStatus(),
                booking.getExpiresAt()
        );
    }

}
