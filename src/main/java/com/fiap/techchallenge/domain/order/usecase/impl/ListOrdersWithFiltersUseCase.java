package com.fiap.techchallenge.domain.order.usecase.impl;

import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderFilters;
import com.fiap.techchallenge.domain.order.OrderRepositoryPort;
import com.fiap.techchallenge.domain.order.usecase.IListOrdersWithFiltersUseCase;

import java.util.List;


public class ListOrdersWithFiltersUseCase implements IListOrdersWithFiltersUseCase {

  private final OrderRepositoryPort repository;
  private final CalculateOrderWaitTimeUseCase calculateOrderWaitTimeUseCase;

  public ListOrdersWithFiltersUseCase(OrderRepositoryPort repository, CalculateOrderWaitTimeUseCase useCase) {
    this.repository = repository;
    this.calculateOrderWaitTimeUseCase = useCase;
  }

  @Override
  public List<Order> execute(OrderFilters filters)
  {
    var orders = repository.getAll(filters);

    for(Order order : orders) {
      var waitTime = calculateOrderWaitTimeUseCase.execute(order);
      order.setWaitingTimeInSeconds(waitTime);
    }

    return orders;
  }

}
