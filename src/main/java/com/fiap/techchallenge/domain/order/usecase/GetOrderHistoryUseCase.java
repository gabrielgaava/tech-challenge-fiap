package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.OrderHistory;
import com.fiap.techchallenge.gateway.OrderGateway;

import java.util.List;
import java.util.UUID;

public class GetOrderHistoryUseCase  {

  private final OrderGateway orderGateway;

  public GetOrderHistoryUseCase(OrderGateway orderGateway) {
    this.orderGateway = orderGateway;
  }

  public List<OrderHistory> execute(UUID id, OrderGateway orderGateway) throws EntityNotFoundException {

    var history = orderGateway.getOrderHistoryByOrderId(id);
    if (history == null) throw new EntityNotFoundException("Order", id);

    return history;

  }

}
