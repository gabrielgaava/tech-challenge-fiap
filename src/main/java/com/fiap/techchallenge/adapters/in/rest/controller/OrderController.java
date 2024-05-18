package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.ErrorDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.UpdateOrderStatusDTO;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.OrderFilters;
import com.fiap.techchallenge.domain.enums.OrderSortFields;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import com.fiap.techchallenge.domain.enums.SortDirection;
import com.fiap.techchallenge.domain.exception.EntityNotFound;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatus;
import com.fiap.techchallenge.domain.usecase.IOrderUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    IOrderUseCase iOrderUseCase;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String orderDirection
    )
    {
        OrderFilters filters = new OrderFilters();
        filters.setStatus(OrderStatus.fromString(status));
        filters.setOrderBy(OrderSortFields.fromString(orderBy));

        orderDirection = orderDirection == null ? "ASC" : orderDirection;
        filters.setDirection(SortDirection.fromString(orderDirection));

        var response = iOrderUseCase.getOrders(filters);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) throws EntityNotFound {
        return iOrderUseCase.getOrder(UUID.fromString(id));
    }

    @GetMapping("/{id}/history")
    public Order getOrderHistory(@PathVariable String id) throws EntityNotFound {
        return iOrderUseCase.getOrder(UUID.fromString(id));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTO request)
    {
        var order = iOrderUseCase.createOrder(request);

        if(order == null) return ResponseEntity.badRequest().body(null);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(order);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable String id, @Valid
            @RequestBody UpdateOrderStatusDTO request
    ) throws OrderAlreadyWithStatus
    {
        var oderId = UUID.fromString(id);
        var status = OrderStatus.fromString(request.getStatus().toUpperCase());
        boolean updated = iOrderUseCase.updateOrderStatus(oderId, status);

        if(updated) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }


}
