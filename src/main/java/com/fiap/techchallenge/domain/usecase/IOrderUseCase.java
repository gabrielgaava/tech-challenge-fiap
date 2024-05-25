package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.OrderFilters;
import com.fiap.techchallenge.domain.entity.OrderHistory;
import com.fiap.techchallenge.domain.entity.Payment;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.MercadoPagoUnavailableException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.exception.OrderNotReadyException;

import java.util.List;
import java.util.UUID;

public interface IOrderUseCase {

    List<Order> getOrders(OrderFilters filters);

    Order getOrder(UUID id) throws EntityNotFoundException;

    Order createOrder(Order dto) throws EntityNotFoundException;

    List<OrderHistory> getOrderHistory(UUID id) throws EntityNotFoundException;

    boolean updateOrderStatus(UUID id, OrderStatus status) throws OrderAlreadyWithStatusException, EntityNotFoundException;

    Payment payOrder(UUID orderId) throws EntityNotFoundException, OrderNotReadyException, MercadoPagoUnavailableException;
}
