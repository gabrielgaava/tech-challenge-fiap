package com.fiap.techchallenge.domain.order.usecase.impl;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.order.OrderRepositoryPort;
import com.fiap.techchallenge.domain.order.OrderStatus;
import com.fiap.techchallenge.domain.order.usecase.IUpdateOrderStatusUseCase;

import java.util.UUID;

import static com.fiap.techchallenge.domain.order.OrderStatus.*;

public class UpdateOrderStatusUseCase implements IUpdateOrderStatusUseCase {

  private final OrderRepositoryPort orderRepository;

  public UpdateOrderStatusUseCase(OrderRepositoryPort repositoryPort) {
    this.orderRepository = repositoryPort;
  }

  public boolean execute(UUID id, OrderStatus status) throws OrderAlreadyWithStatusException, EntityNotFoundException {
    var order = orderRepository.getById(id);

    // Order Not Found in Database
    if (order == null) throw new EntityNotFoundException("Order", id);

    switch (status) {

      // Invalid Status from Payload
      case null: {
        throw new IllegalArgumentException("Status cannot be null and must be a valid status: CREATED, " +
            "RECEIVED, IN_PREPARATION, READY_TO_DELIVERY, CANCELED or FINISHED");
      }

      // Forbidden route usage
      case RECEIVED: {
        throw new IllegalArgumentException("To update to this status use payment route");
      }

      // RECEIVED -> IN_PREPARATION Validation
      case IN_PREPARATION: {
        if (order.getStatus() != RECEIVED)
          throw new IllegalArgumentException("Order must be in 'RECEIVED' status");
        break;
      }

      // IN_PREPARATION -> READY_TO_DELIVERY Validation
      case READY_TO_DELIVERY: {
        if (order.getStatus() != IN_PREPARATION)
          throw new IllegalArgumentException("Order must be in 'IN_PREPARATION' status");
        break;
      }

      // READY_TO_DELIVERY -> FINISHED Validation
      case FINISHED: {
        if (order.getStatus() != READY_TO_DELIVERY)
          throw new IllegalArgumentException("Order must be in 'READY_TO_DELIVERY' status");
        break;
      }

      // CANCELED not update status
      case CREATED: {
        if (order.getStatus() != CREATED)
          throw new IllegalArgumentException("Not possible to change the status of an order to 'CREATED'.");
        break;
      }

      // FINISHED not update status
      case CANCELED: {
        if (order.getStatus() == FINISHED)
          throw new IllegalArgumentException("Not possible to change the status of an order to 'FINISHED'.");
        break;
      }

      default: {
        break;
      }

    }

    // Order with the same status
    if (order.getStatus().equals(status))
      throw new OrderAlreadyWithStatusException(id, status);

    return orderRepository.updateStatus(order, status, order.getStatus()) == 2;
  }

}
