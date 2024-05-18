package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.adapters.in.rest.handler.GalegaException;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IOrderRepository {

    List<Order> getAll();

    Order getOrder(String orderNumber);

    void updateOrderStatus(String orderNumber, OrderStatus status) throws GalegaException;

    int create(Order order);

}
