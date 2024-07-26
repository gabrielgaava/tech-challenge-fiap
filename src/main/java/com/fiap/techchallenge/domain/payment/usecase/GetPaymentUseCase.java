package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;

import java.util.UUID;

public class GetPaymentUseCase {

  private final PaymentGateway paymentGateway;

  public GetPaymentUseCase(PaymentGateway paymentGateway) {
    this.paymentGateway = paymentGateway;
  }

  public Payment execute(UUID id, PaymentGateway paymentGateway) {
    return this.paymentGateway.get(id);
  }

  public Payment execute(String externalId, PaymentGateway paymentGateway) {
    return this.paymentGateway.getByExternalId(externalId);
  }

}
