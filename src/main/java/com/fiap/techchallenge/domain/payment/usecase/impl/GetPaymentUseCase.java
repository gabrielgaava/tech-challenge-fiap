package com.fiap.techchallenge.domain.payment.usecase.impl;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.PaymentRepositoryPort;
import com.fiap.techchallenge.domain.payment.usecase.IGetPaymentUseCase;

import java.util.UUID;

public class GetPaymentUseCase implements IGetPaymentUseCase {

  private final PaymentRepositoryPort paymentRepository;

  public GetPaymentUseCase(PaymentRepositoryPort paymentRepository) {
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
