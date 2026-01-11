package com.ticketsystem.payments.repository;

import com.ticketsystem.payments.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByProviderRef(String providerRef);
    Optional<Payment> findTopByBookingIdOrderByCreatedAtDesc(Long bookingId);
}
