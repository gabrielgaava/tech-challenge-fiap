package com.fiap.techchallenge.domain.order;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepositoryPort {

    List<Order> getAll(OrderFilters filters);

    List<OrderHistory> getOrderHistoryByOrderId(UUID orderId);

    Order getById(UUID id);

    Order getByIdWithProducts(UUID id);

    int create(Order order);

    int updateStatus(Order order, OrderStatus newStatus, OrderStatus previousStatus);
}
