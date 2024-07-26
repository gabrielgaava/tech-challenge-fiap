package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;

public class UpdatePaymentUseCase  {

  private final PaymentGateway paymentGateway;

  public UpdatePaymentUseCase(PaymentGateway paymentGateway) {
    this.paymentGateway = paymentGateway;
  }

  public Payment execute(Payment payment) {
    if(paymentGateway.update(payment)) return payment;
    return null;
  }

}
