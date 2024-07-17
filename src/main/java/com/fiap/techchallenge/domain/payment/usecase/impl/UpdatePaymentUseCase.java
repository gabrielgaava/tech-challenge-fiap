package com.fiap.techchallenge.domain.payment.usecase.impl;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.PaymentRepositoryPort;
import com.fiap.techchallenge.domain.payment.usecase.IUpdatePaymentUseCase;

public class UpdatePaymentUseCase implements IUpdatePaymentUseCase {

  private final PaymentRepositoryPort paymentRepository;

  public UpdatePaymentUseCase(PaymentRepositoryPort paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Override
  public Payment execute(Payment payment) {
    if(paymentRepository.update(payment)) return payment;
    return null;
  }

}
