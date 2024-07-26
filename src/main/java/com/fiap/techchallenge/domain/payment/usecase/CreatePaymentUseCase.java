package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;

public class CreatePaymentUseCase {

  private final PaymentGateway paymentGateway;

  public CreatePaymentUseCase(PaymentGateway paymentGateway) {
    this.paymentGateway = paymentGateway;
  }

  public Payment execute(Payment payment) {
    if(paymentGateway.create(payment) == 1) return payment;
    return null;
  }

}
