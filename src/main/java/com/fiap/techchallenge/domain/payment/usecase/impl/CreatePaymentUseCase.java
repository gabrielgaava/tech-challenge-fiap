package com.fiap.techchallenge.domain.payment.usecase.impl;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;
import com.fiap.techchallenge.domain.payment.usecase.ICreatePaymentUseCase;

public class CreatePaymentUseCase implements ICreatePaymentUseCase {

  private final PaymentGateway paymentRepository;

  public CreatePaymentUseCase(PaymentGateway paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Override
  public Payment execute(Payment payment) {
    if(paymentRepository.create(payment) == 1) return payment;
    return null;
  }

}
