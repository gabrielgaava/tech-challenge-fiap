package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.OrderHistory;

import java.util.List;
import java.util.UUID;

public interface IGetOrderHistoryUseCase {

  /**
   * Gets all the order's history, with status changes registers
   * @param id The Order's ID
   * @return all order's history with status update
   * @throws EntityNotFoundException
   */
  List<OrderHistory> execute(UUID id) throws EntityNotFoundException;

}
