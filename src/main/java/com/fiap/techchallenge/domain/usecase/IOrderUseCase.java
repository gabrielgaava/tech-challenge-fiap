package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.OrderFilters;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import com.fiap.techchallenge.domain.exception.EntityNotFound;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatus;

import java.util.List;
import java.util.UUID;

public interface IOrderUseCase {

    List<Order> getOrders(OrderFilters filters);

    Order getOrder(UUID id) throws EntityNotFound;

    Order createOrder(CreateOrderDTO dto);

    boolean updateOrderStatus(UUID id, OrderStatus status) throws OrderAlreadyWithStatus;

}
