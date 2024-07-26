package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;

import java.util.UUID;

public class GetPaymentUseCase {

  private final PaymentGateway paymentRepository;

  public GetPaymentUseCase(PaymentGateway paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public Payment execute(UUID id) {
    return this.paymentRepository.get(id);
  }

  public Payment execute(String externalId) {
    return this.paymentRepository.getByExternalId(externalId);
  }

}
