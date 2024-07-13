package com.fiap.techchallenge.domain.service;

import com.fiap.techchallenge.adapters.out.database.postgress.OderRepository;
import com.fiap.techchallenge.adapters.out.database.postgress.PaymentRepository;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.Payment;
import com.fiap.techchallenge.domain.enums.PaymentStatus;
import com.fiap.techchallenge.domain.repository.OrderRepositoryPort;
import com.fiap.techchallenge.domain.repository.PaymentRepositoryPort;
import com.fiap.techchallenge.domain.usecase.IPaymentUseCase;

import javax.sql.DataSource;
import java.util.UUID;

public class PaymentService implements IPaymentUseCase {

  private final PaymentRepositoryPort repository;

  public PaymentService(DataSource dataSource) {
    this.repository = new PaymentRepository(dataSource);
  }

  @Override
  public Payment create(Order order, String gatewayName, String externalId, Object transactionalData) {

    Payment payment = new Payment();
    payment.setGateway(gatewayName);
    payment.setId(UUID.randomUUID());
    payment.setExternalId(externalId);
    payment.setAmount(order.getAmount());
    payment.setPayedAt(null);
    payment.setOrderId(order.getId());
    payment.setStatus(PaymentStatus.PENDING.toString());
    payment.setTransactionData(transactionalData);

    if(repository.create(payment) == 1) {
      return payment;
    }

    return null;

  }

  @Override
  public Payment get(UUID id) {
    return repository.get(id);
  }

  @Override
  public Payment getByExternalId(String externalId) {
    return repository.getByExternalId(externalId);
  }

  @Override
  public Payment getAll() {
    return null;
  }

  @Override
  public Payment update(Payment payment) {
    if(repository.update(payment)) {
      return payment;
    }

    return null;
  }
}
