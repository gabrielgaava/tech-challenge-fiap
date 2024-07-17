package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderFilters;

import java.util.List;

public interface IListOrdersWithFiltersUseCase {

  /**
   * Gets ALL orders stored at the database
   * @param filters: database filter queries
   * @return: the filtered list of orders
   */
  List<Order> execute(OrderFilters filters);

}
