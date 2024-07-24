package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.usecase.IGetPaymentUseCase;

import java.util.UUID;


public class PaymentController {

  private final IGetPaymentUseCase getPaymentUseCase;

  public PaymentController(IGetPaymentUseCase getPaymentUseCase) {
    this.getPaymentUseCase = getPaymentUseCase;
  }


  public Payment getPayment(String paymentId){
      return getPaymentUseCase.execute(paymentId);
  }

  public Payment getPayment(UUID paymentId){
    return getPaymentUseCase.execute(paymentId);
  }

}
