package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.entity.Payment;

import java.util.UUID;

public interface PaymentRepositoryPort {

  int create(Payment payment);

  Payment getByExternalId(String externalId);

  Payment get(UUID id);

  boolean update(Payment payment);

}
