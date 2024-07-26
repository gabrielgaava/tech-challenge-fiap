package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.gateway.OrderGateway;

import java.util.UUID;

public class GetOrderUseCase  {

  private final OrderGateway Gateway;
  private final CalculateOrderWaitTimeUseCase calculateOrderWaitTimeUseCase;

  public GetOrderUseCase(OrderGateway Gateway, CalculateOrderWaitTimeUseCase calculateOrderWaitTimeUseCase) {
    this.Gateway = Gateway;
    this.calculateOrderWaitTimeUseCase = calculateOrderWaitTimeUseCase;
  }

  public Order execute(UUID id, OrderGateway orderGateway) throws EntityNotFoundException {
    var order = Gateway.getByIdWithProducts(id);

    if (order == null) throw new EntityNotFoundException("Order", id);

    var history = Gateway.getOrderHistoryByOrderId(id);

    order.setWaitingTimeInSeconds(calculateOrderWaitTimeUseCase.execute(order));
    order.setHistory(history);

    return order;
  }

}
