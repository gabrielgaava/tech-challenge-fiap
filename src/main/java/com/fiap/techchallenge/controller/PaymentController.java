package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.usecase.GetPaymentUseCase;
import com.fiap.techchallenge.gateway.PaymentGateway;

import java.util.UUID;


public class PaymentController {

  private final GetPaymentUseCase getPaymentUseCase;

  public PaymentController(GetPaymentUseCase getPaymentUseCase) {
    this.getPaymentUseCase = getPaymentUseCase;
  }


  public Payment getPayment(String paymentId, PaymentGateway paymentGateway){
      return getPaymentUseCase.execute(paymentId);
  }

  public Payment getPayment(UUID paymentId, PaymentGateway paymentGateway){
    return getPaymentUseCase.execute(paymentId);
  }

}
