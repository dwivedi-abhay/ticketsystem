package com.ticketsystem.booking.workflow;


import com.ticketsystem.booking.domain.Booking;
import com.ticketsystem.booking.domain.BookingStatus;
import org.springframework.stereotype.Component;

@Component
public class BookingStateMachine {

    public boolean canMoveToPendingPayment(Booking booking){
        return booking.getStatus() == BookingStatus.INITIATED;
    }

    public boolean canConfirmBooking(Booking booking){
        return booking.getStatus() == BookingStatus.PAYMENT_PENDING;
    }

    public boolean canExpire(Booking booking){
        return booking.getStatus() == BookingStatus.INITIATED ||
                booking.getStatus() == BookingStatus.EXPIRED ||
                booking.getStatus() == BookingStatus.PAYMENT_PENDING;
    }
}
