package com.fiap.techchallenge.domain.order.usecase.impl;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.OrderHistory;
import com.fiap.techchallenge.domain.order.OrderRepositoryPort;
import com.fiap.techchallenge.domain.order.usecase.IGetOrderHistoryUseCase;

import java.util.List;
import java.util.UUID;

public class GetOrderHistoryUseCase implements IGetOrderHistoryUseCase {

  private final OrderRepositoryPort orderRepository;

  public GetOrderHistoryUseCase(OrderRepositoryPort orderRepository) {
    this.orderRepository = orderRepository;
  }

  public List<OrderHistory> execute(UUID id) throws EntityNotFoundException {

    var history = orderRepository.getOrderHistoryByOrderId(id);
    if (history == null) throw new EntityNotFoundException("Order", id);

    return history;

  }

}
