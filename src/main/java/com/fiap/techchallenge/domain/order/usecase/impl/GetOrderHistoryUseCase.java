package com.fiap.techchallenge.domain.order.usecase.impl;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.OrderHistory;
import com.fiap.techchallenge.gateway.OrderGateway;
import com.fiap.techchallenge.domain.order.usecase.IGetOrderHistoryUseCase;

import java.util.List;
import java.util.UUID;

public class GetOrderHistoryUseCase implements IGetOrderHistoryUseCase {

  private final OrderGateway orderRepository;

  public GetOrderHistoryUseCase(OrderGateway orderRepository) {
    this.orderRepository = orderRepository;
  }

  public List<OrderHistory> execute(UUID id) throws EntityNotFoundException {

    var history = orderRepository.getOrderHistoryByOrderId(id);
    if (history == null) throw new EntityNotFoundException("Order", id);

    return history;

  }

}
