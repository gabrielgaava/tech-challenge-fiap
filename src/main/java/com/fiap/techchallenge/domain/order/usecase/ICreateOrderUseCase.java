package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.Order;

public interface ICreateOrderUseCase {

   /**
   * Create a new order with RECEIVED status
   * @param order The basic order data to create and storage at database
   * @return the created order object or null, in case of error
   */
  Order execute(Order order) throws EntityNotFoundException;

}
