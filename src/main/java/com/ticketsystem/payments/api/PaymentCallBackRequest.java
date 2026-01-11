package com.ticketsystem.payments.api;

public record PaymentCallBackRequest(
        Long bookingId,
        String providerRef,
        boolean success

) {
}
