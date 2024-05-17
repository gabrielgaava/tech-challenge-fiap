package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IOrderRepository {

    List<Order> getAll();

    Order getOrder(UUID orderId);

    int create(Order order);

}
