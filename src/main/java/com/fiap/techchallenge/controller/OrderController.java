package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.MercadoPagoUnavailableException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.exception.OrderNotReadyException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderFilters;
import com.fiap.techchallenge.domain.order.OrderHistory;
import com.fiap.techchallenge.domain.order.OrderStatus;
import com.fiap.techchallenge.domain.order.usecase.*;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.OrderGateway;

import java.util.List;
import java.util.UUID;

public class OrderController {

    private final GetOrderUseCase getOrderUseCase;
    private final ListOrdersWithFiltersUseCase listOrdersWithFiltersUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final CheckoutOrderUseCase checkoutOrderUseCase;
    private final GetOrderHistoryUseCase getOrderHistoryUseCase;
    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(
       GetOrderUseCase getOrderUseCase,
       ListOrdersWithFiltersUseCase listOrdersWithFiltersUseCase,
       UpdateOrderStatusUseCase updateOrderStatusUseCase,
       CheckoutOrderUseCase checkoutOrderUseCase,
       GetOrderHistoryUseCase getOrderHistoryUseCase,
       CreateOrderUseCase createOrderUseCase
    ) {
        this.getOrderUseCase = getOrderUseCase;
        this.listOrdersWithFiltersUseCase = listOrdersWithFiltersUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.checkoutOrderUseCase = checkoutOrderUseCase;
        this.getOrderHistoryUseCase = getOrderHistoryUseCase;
        this.createOrderUseCase = createOrderUseCase;
    }

    public List<Order> getOrders(OrderFilters filters, OrderGateway orderGateway){
       return listOrdersWithFiltersUseCase.execute(filters, orderGateway);
    }

    public Order getOrder(String id, OrderGateway orderGateway) throws EntityNotFoundException {
        return getOrderUseCase.execute(UUID.fromString(id), orderGateway);
    }

    public List<OrderHistory> getOrderHistory(UUID id, OrderGateway orderGateway) throws EntityNotFoundException {
        return getOrderHistoryUseCase.execute(id, orderGateway);
    }

    public Order createOrder(Order order, OrderGateway orderGateway) throws EntityNotFoundException {
        return createOrderUseCase.execute(order, orderGateway);
    }

    public boolean updateStatus(UUID orderId, OrderStatus status, OrderGateway orderGateway) throws EntityNotFoundException, OrderAlreadyWithStatusException {
        return updateOrderStatusUseCase.execute(orderId, status, orderGateway);
    }

    public Payment payOrder(UUID orderId, OrderGateway orderGateway) throws MercadoPagoUnavailableException, EntityNotFoundException, OrderNotReadyException {
        return checkoutOrderUseCase.execute(orderId, orderGateway);
    }

}
