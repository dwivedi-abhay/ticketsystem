package com.ticketsystem.payments.api;

import com.ticketsystem.booking.services.BookingService;
import com.ticketsystem.payments.service.PaymentResult;
import com.ticketsystem.payments.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentCallbackController {

    private final PaymentService paymentService;
    private final BookingService bookingService;

    public PaymentCallbackController(
            PaymentService paymentService,
            BookingService bookingService
    ){
        this.bookingService = bookingService;
        this.paymentService = paymentService;
    }

    @PostMapping("/callback")
    public void handleCallback(@RequestBody PaymentCallBackRequest request){
        PaymentResult result = paymentService.processPaymentRequest(request);

        if(result == PaymentResult.SUCCESS){
            bookingService.confirmBooking(request.bookingId());
        }
        else if(result == PaymentResult.FAILURE){
            bookingService.expireBooking(request.bookingId());
        }

    }

}
