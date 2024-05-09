package com.fiap.techchallenge.domain.service;

import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.repository.IOrderRepository;
import com.fiap.techchallenge.domain.usecase.IOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderUseCase {

    @Autowired
    IOrderRepository orderRepository;

    @Override
    public List<Order> getOrders() {
        return orderRepository.getOrders();
    }

}
