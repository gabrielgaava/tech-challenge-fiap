package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;

public class UpdatePaymentUseCase  {

  private final PaymentGateway paymentRepository;

  public UpdatePaymentUseCase(PaymentGateway paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public Payment execute(Payment payment) {
    if(paymentRepository.update(payment)) return payment;
    return null;
  }

}
