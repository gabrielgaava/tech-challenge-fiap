package com.fiap.techchallenge.domain.payment.usecase.impl;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.PaymentRepositoryPort;
import com.fiap.techchallenge.domain.payment.usecase.ICreatePaymentUseCase;

public class CreatePaymentUseCase implements ICreatePaymentUseCase {

  private final PaymentRepositoryPort paymentRepository;

  public CreatePaymentUseCase(PaymentRepositoryPort paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Override
  public Payment execute(Payment payment) {
    if(paymentRepository.create(payment) == 1) return payment;
    return null;
  }

}
