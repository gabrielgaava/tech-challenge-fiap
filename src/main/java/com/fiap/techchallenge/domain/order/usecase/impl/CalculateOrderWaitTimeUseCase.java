package com.fiap.techchallenge.domain.order.usecase.impl;

import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderStatus;
import com.fiap.techchallenge.domain.order.usecase.ICalculateOrderWaitTimeUseCase;

import java.time.Duration;
import java.time.LocalDateTime;
import static com.fiap.techchallenge.domain.order.OrderStatus.CREATED;

public class CalculateOrderWaitTimeUseCase implements ICalculateOrderWaitTimeUseCase {

  public long execute(Order order) {
    OrderStatus status = order.getStatus();

    // Invalid states to count waiting time
    if (status.equals(CREATED)
        || status.equals(OrderStatus.FINISHED)
        || status.equals(OrderStatus.CANCELED))
      return 0;

    // Error retrieving paid at date
    if (order.getPaidAt() == null)
      return 0;

    return Duration.between(order.getPaidAt(), LocalDateTime.now()).toSeconds();
  }

}
