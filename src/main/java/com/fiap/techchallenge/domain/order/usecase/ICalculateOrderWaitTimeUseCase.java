package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.fiap.techchallenge.domain.order.OrderStatus.CREATED;

public interface ICalculateOrderWaitTimeUseCase {

  /**
   * Calculate the total of time in seconds that the order was created until now
   * @param order: The Oder that will have the time waiting calculated
   * @return the total time of wait in seconds
   */
  long execute(Order order);

}
