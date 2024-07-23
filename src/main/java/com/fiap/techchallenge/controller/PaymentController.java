package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.usecase.IGetPaymentUseCase;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


public class PaymentController {

  private final IGetPaymentUseCase getPaymentUseCase;

  public PaymentController(IGetPaymentUseCase getPaymentUseCase) {
    this.getPaymentUseCase = getPaymentUseCase;
  }


  public ResponseEntity<Payment> getPayment(
      String paymentId,
      boolean isExternal
  ) {
    if (isExternal) {
      return ResponseEntity.ok(getPaymentUseCase.execute(paymentId));
    }

    return ResponseEntity.ok(getPaymentUseCase.execute(UUID.fromString(paymentId)));
  }

}
