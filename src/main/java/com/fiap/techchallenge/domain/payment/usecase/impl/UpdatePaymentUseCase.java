package com.fiap.techchallenge.domain.payment.usecase.impl;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;
import com.fiap.techchallenge.domain.payment.usecase.IUpdatePaymentUseCase;

public class UpdatePaymentUseCase implements IUpdatePaymentUseCase {

  private final PaymentGateway paymentRepository;

  public UpdatePaymentUseCase(PaymentGateway paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Override
  public Payment execute(Payment payment) {
    if(paymentRepository.update(payment)) return payment;
    return null;
  }

}
