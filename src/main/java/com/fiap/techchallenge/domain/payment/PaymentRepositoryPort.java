package com.fiap.techchallenge.domain.payment;

import java.util.UUID;

public interface PaymentRepositoryPort {

  int create(Payment payment);

  Payment getByExternalId(String externalId);

  Payment get(UUID id);

  boolean update(Payment payment);

}
