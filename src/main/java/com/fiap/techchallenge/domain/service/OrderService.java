package com.fiap.techchallenge.domain.service;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import com.fiap.techchallenge.domain.repository.IOrderRepository;
import com.fiap.techchallenge.domain.usecase.IOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements IOrderUseCase {

    @Autowired
    @Qualifier("PGOrderRepository")
    IOrderRepository IOrderRepository;

    @Override
    public List<Order> getOrders() {
        return IOrderRepository.getAll();
    }

    @Override
    public Order createOrder(CreateOrderDTO dto) {

        UUID costumerId = dto.getCostumerId() != null
                ? UUID.fromString(dto.getCostumerId())
                : null;

        var order = Order.builder()
            .id(UUID.randomUUID())
            .costumerId(costumerId)
            .orderNumber("123") //TODO: Como criar esse numero ?
            .amount(dto.getAmount())
            .status(OrderStatus.RECEIVED)
            .createdAt(LocalDateTime.now())
            .build();

        if(IOrderRepository.create(order) == 1)
            return order;

        else return null;
    }

}
