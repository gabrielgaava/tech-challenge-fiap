package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.adapters.in.rest.handler.GalegaException;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.Payment;

import java.util.List;

public interface IOrderUseCase {

    List<Order> getOrders();

    Order createOrder(CreateOrderDTO dto);

    Payment payOrder(String orderNumber) throws GalegaException;

}
