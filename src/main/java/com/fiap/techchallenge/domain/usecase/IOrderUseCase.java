package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.domain.entity.Order;

import java.util.List;

public interface IOrderUseCase {

    List<Order> getOrders();

}