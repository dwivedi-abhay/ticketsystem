package com.ticketsystem.payments.service;


import com.ticketsystem.payments.Payment;
import com.ticketsystem.payments.PaymentStatus;
import com.ticketsystem.payments.api.PaymentCallBackRequest;
import com.ticketsystem.payments.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    public PaymentResult processPaymentRequest(PaymentCallBackRequest request){

        Payment payment = paymentRepository.findByProviderRef(request.providerRef()).orElseThrow(
                () -> new IllegalStateException("Unknown Payment"));

        if(payment.getPaymentStatus() != PaymentStatus.PENDING){
            return PaymentResult.IGNORED;
        }

        if(request.success()){
            payment.markSuccess();
            paymentRepository.save(payment);
            return PaymentResult.SUCCESS;
        }
        else{
            payment.markFailed();;
            paymentRepository.save(payment);
            return PaymentResult.FAILURE;
        }
    }


}
