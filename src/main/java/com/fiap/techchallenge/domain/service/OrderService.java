package com.fiap.techchallenge.domain.service;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.OrderProductDTO;
import com.fiap.techchallenge.domain.entity.OrderFilters;
import com.fiap.techchallenge.domain.entity.ProductAndQuantity;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import com.fiap.techchallenge.domain.exception.EntityNotFound;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatus;
import com.fiap.techchallenge.domain.repository.IOrderRepository;
import com.fiap.techchallenge.domain.repository.IProductRepository;
import com.fiap.techchallenge.domain.usecase.IOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.math.RoundingMode.HALF_EVEN;

@Service
public class OrderService implements IOrderUseCase {

    @Autowired
    @Qualifier("PGOrderRepository")
    IOrderRepository IOrderRepository;

    @Autowired
    @Qualifier("PGProductRepository")
    IProductRepository IProductRepository;

    @Override
    public List<Order> getOrders(OrderFilters filters) {
        var orders = IOrderRepository.getAll(filters);

        for(Order order : orders) {
            var waitTime = calculateWaitTime(order.getCreatedAt());
            order.setWaitingTimeInSeconds(waitTime);
        }

        return orders;
    }

    @Override
    public Order getOrder(UUID id) throws EntityNotFound {
        var order = IOrderRepository.getByIdWithProducts(id);

        if(order == null) throw new EntityNotFound("Order", id);

        var history = IOrderRepository.getOrderHistoryByOrderId(id);

        order.setWaitingTimeInSeconds(calculateWaitTime(order.getCreatedAt()));
        order.setHistory(history);

        return order;
    }

    @Override
    public Order createOrder(CreateOrderDTO dto) {

        UUID costumerId = dto.getCostumerId() != null
                ? UUID.fromString(dto.getCostumerId())
                : null;

        List<ProductAndQuantity> orderProducts = new ArrayList<>();

        // Retrieve all Products from database from ID's list
        for(OrderProductDTO item : dto.getProducts()) {
            var product = IProductRepository.getById(UUID.fromString(item.getId()));
            if(product != null) {
                orderProducts.add(new ProductAndQuantity(product, item.getQuantity()));
            }
        }

        // Sum all products and multiply its quantities to generate the order amount
        BigDecimal orderAmount = orderProducts
                .stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, HALF_EVEN);

        var order = Order.builder()
            .id(UUID.randomUUID())
            .costumerId(costumerId)
            .orderNumber("123") //TODO: Como criar esse numero ?
            .amount(orderAmount)
            .status(OrderStatus.RECEIVED)
            .createdAt(LocalDateTime.now())
            .products(orderProducts)
            .build();

        if(IOrderRepository.create(order) == 1)
            return order;


        else return null;
    }

    @Override
    public boolean updateOrderStatus(UUID id, OrderStatus status) throws OrderAlreadyWithStatus {
        var order = IOrderRepository.getById(id);

        if(order.getStatus().equals(status))
            throw new OrderAlreadyWithStatus(id, status);

        return IOrderRepository.updateStatus(id, status, order.getStatus()) == 2;
    }

    private long calculateWaitTime(LocalDateTime orderMoment) {
        return Duration.between(orderMoment, LocalDateTime.now()).toSeconds();
    }
}
