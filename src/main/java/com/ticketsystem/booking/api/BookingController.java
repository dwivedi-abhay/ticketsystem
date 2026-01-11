package com.ticketsystem.booking.api;


import com.ticketsystem.booking.domain.Booking;
import com.ticketsystem.booking.services.BookingService;
import com.ticketsystem.security.auth.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponse createBooking(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody CreateBookingRequest request
    ){
        Booking booking = bookingService.createBooking(user.getUserId(), request.showId(), request.seatIds());

        return toResponse(booking);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse getBooking(@PathVariable Long bookingId, @AuthenticationPrincipal UserPrincipal user){
        Booking booking = bookingService.getBooking(bookingId, user.getUserId());
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
