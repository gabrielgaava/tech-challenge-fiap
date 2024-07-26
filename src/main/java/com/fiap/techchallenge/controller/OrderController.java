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

    public List<Order> getOrders(OrderFilters filters){
       return listOrdersWithFiltersUseCase.execute(filters);
    }

    public Order getOrder(String id) throws EntityNotFoundException {
        return getOrderUseCase.execute(UUID.fromString(id));
    }

    public List<OrderHistory> getOrderHistory(UUID id) throws EntityNotFoundException {
        return getOrderHistoryUseCase.execute(id);
    }

    public Order createOrder(Order order) throws EntityNotFoundException {
        return createOrderUseCase.execute(order);
    }

    public boolean updateStatus(UUID orderId, OrderStatus status) throws EntityNotFoundException, OrderAlreadyWithStatusException {
        return updateOrderStatusUseCase.execute(orderId, status);
    }

    public Payment payOrder(UUID orderId) throws MercadoPagoUnavailableException, EntityNotFoundException, OrderNotReadyException {
        return checkoutOrderUseCase.execute(orderId);
    }

}
