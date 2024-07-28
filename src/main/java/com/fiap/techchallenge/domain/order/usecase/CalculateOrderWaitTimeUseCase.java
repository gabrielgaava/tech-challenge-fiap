package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.fiap.techchallenge.domain.order.OrderStatus.CREATED;

public class CalculateOrderWaitTimeUseCase {

  public long execute(Order order) {
    OrderStatus status = order.getStatus();

    if (order.getPaidAt() == null) {
      return 0;
    }

    // Invalid states to count waiting time
    if (status.equals(CREATED)
        || status.equals(OrderStatus.FINISHED)
        || status.equals(OrderStatus.CANCELED))
      return 0;

    return Duration.between(order.getPaidAt(), LocalDateTime.now()).toSeconds();
  }

}
