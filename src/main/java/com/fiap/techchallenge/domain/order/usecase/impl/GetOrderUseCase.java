package com.fiap.techchallenge.domain.order.usecase.impl;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderRepositoryPort;
import com.fiap.techchallenge.domain.order.usecase.IGetOrderUseCase;

import java.util.UUID;

public class GetOrderUseCase implements IGetOrderUseCase {

  private final OrderRepositoryPort repository;
  private final CalculateOrderWaitTimeUseCase calculateOrderWaitTimeUseCase;

  public GetOrderUseCase(OrderRepositoryPort repository, CalculateOrderWaitTimeUseCase calculateOrderWaitTimeUseCase) {
    this.repository = repository;
    this.calculateOrderWaitTimeUseCase = calculateOrderWaitTimeUseCase;
  }

  public Order execute(UUID id) throws EntityNotFoundException {
    var order = repository.getByIdWithProducts(id);

    if (order == null) throw new EntityNotFoundException("Order", id);

    var history = repository.getOrderHistoryByOrderId(id);

    order.setWaitingTimeInSeconds(calculateOrderWaitTimeUseCase.execute(order));
    order.setHistory(history);

    return order;
  }

}
