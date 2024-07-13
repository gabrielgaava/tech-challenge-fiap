package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.Payment;

import java.util.UUID;

public interface IPaymentUseCase {

  Payment create(Order order, String gatewayName, String externalId, Object transactionData);

  Payment get(UUID id);

  Payment getByExternalId(String externalId);

  Payment getAll();

  Payment update(Payment payment);

}
