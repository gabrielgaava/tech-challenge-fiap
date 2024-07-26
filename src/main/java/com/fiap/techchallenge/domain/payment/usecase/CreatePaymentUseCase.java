package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;

public class CreatePaymentUseCase {

  private final PaymentGateway paymentRepository;

  public CreatePaymentUseCase(PaymentGateway paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public Payment execute(Payment payment) {
    if(paymentRepository.create(payment) == 1) return payment;
    return null;
  }

}
