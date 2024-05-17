package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.adapters.in.rest.handler.GalegaException;
import com.fiap.techchallenge.domain.entity.Payment;
import com.fiap.techchallenge.domain.entity.Order;

import java.util.List;
import java.util.UUID;

public interface IOrderUseCase {

    List<Order> getOrders();

    Order createOrder(CreateOrderDTO dto);

    Payment payOrder(UUID id) throws GalegaException;

}
