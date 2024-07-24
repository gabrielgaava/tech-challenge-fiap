package com.fiap.techchallenge.domain.payment.usecase.impl;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;
import com.fiap.techchallenge.domain.payment.usecase.IGetPaymentUseCase;

import java.util.UUID;

public class GetPaymentUseCase implements IGetPaymentUseCase {

  private final PaymentGateway paymentRepository;

  public GetPaymentUseCase(PaymentGateway paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Override
  public Payment execute(UUID id) {
    return this.paymentRepository.get(id);
  }

  @Override
  public Payment execute(String externalId) {
    return this.paymentRepository.getByExternalId(externalId);
  }

}
