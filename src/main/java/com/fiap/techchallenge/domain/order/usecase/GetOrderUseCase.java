package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.gateway.OrderGateway;

import java.util.UUID;

public class GetOrderUseCase  {

  private final OrderGateway repository;
  private final CalculateOrderWaitTimeUseCase calculateOrderWaitTimeUseCase;

  public GetOrderUseCase(OrderGateway repository, CalculateOrderWaitTimeUseCase calculateOrderWaitTimeUseCase) {
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
