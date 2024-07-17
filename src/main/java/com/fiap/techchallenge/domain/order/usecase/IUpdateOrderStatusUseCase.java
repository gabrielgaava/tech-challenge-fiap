package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.order.OrderStatus;

import java.util.UUID;

import static com.fiap.techchallenge.domain.order.OrderStatus.*;

public interface IUpdateOrderStatusUseCase {

   /**
   * Updates the order's status
   *
   * @param id     The order ID
   * @param status The new order Status
   * @return true if updated successfully, false otherwise
   * @throws OrderAlreadyWithStatusException
   */
  boolean execute(UUID id, OrderStatus status) throws OrderAlreadyWithStatusException, EntityNotFoundException;

}
