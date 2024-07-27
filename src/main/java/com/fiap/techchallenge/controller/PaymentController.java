package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.usecase.GetPaymentUseCase;
import com.fiap.techchallenge.gateway.PaymentGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentController {

  private final GetPaymentUseCase getPaymentUseCase;

  public PaymentController(GetPaymentUseCase getPaymentUseCase) {
    this.getPaymentUseCase = getPaymentUseCase;
  }


  public Payment getPayment(String paymentId, PaymentGateway paymentGateway){
      return getPaymentUseCase.execute(paymentId, paymentGateway);
  }

  public Payment getPayment(UUID paymentId, PaymentGateway paymentGateway){
    return getPaymentUseCase.execute(paymentId, paymentGateway);
  }

}
