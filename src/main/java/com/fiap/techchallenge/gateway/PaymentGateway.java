package com.fiap.techchallenge.gateway;

import com.fiap.techchallenge.domain.payment.Payment;

import java.util.UUID;

public interface PaymentGateway {

  int create(Payment payment);

  Payment getByExternalId(String externalId);

  Payment get(UUID id);

  boolean update(Payment payment);

}
