package com.fiap.techchallenge.gateway;

import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderFilters;
import com.fiap.techchallenge.domain.order.OrderHistory;
import com.fiap.techchallenge.domain.order.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderGateway {

    List<Order> getAll(OrderFilters filters);

    List<OrderHistory> getOrderHistoryByOrderId(UUID orderId);

    Order getById(UUID id);

    Order getByIdWithProducts(UUID id);

    int create(Order order);

    int updateStatus(Order order, OrderStatus newStatus, OrderStatus previousStatus);
}
