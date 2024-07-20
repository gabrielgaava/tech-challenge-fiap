package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.Order;

import java.util.UUID;

public interface IGetOrderUseCase {

  Order execute(UUID id) throws EntityNotFoundException;

}
