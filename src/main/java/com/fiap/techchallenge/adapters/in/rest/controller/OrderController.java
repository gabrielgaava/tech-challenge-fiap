package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.ErrorDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.UpdateOrderStatusDTO;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.OrderFilters;
import com.fiap.techchallenge.domain.entity.OrderHistory;
import com.fiap.techchallenge.domain.enums.OrderSortFields;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import com.fiap.techchallenge.domain.enums.SortDirection;
import com.fiap.techchallenge.domain.exception.EntityNotFound;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatus;
import com.fiap.techchallenge.domain.usecase.IOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Order Controller")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    IOrderUseCase iOrderUseCase;

    @Operation(
      summary = "List all orders based on query filters",
      parameters = {
        @Parameter(name = "status", schema = @Schema(implementation = OrderStatus.class)),
        @Parameter(name = "orderBy", schema = @Schema(implementation = OrderSortFields.class)),
        @Parameter(name = "orderDirection", schema = @Schema(implementation = SortDirection.class)),
        @Parameter(name = "expandProducts", schema = @Schema(implementation = Boolean.class), description = "Bring the list of products to each order")
    })
    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String orderDirection,
            @RequestParam(required = false) Boolean expandProducts
    )
    {
        OrderFilters filters = new OrderFilters();
        filters.setStatus(OrderStatus.fromString(status));
        filters.setOrderBy(OrderSortFields.fromString(orderBy));
        filters.setExpandProducts(expandProducts);

        orderDirection = orderDirection == null ? "ASC" : orderDirection;
        filters.setDirection(SortDirection.fromString(orderDirection));

        var response = iOrderUseCase.getOrders(filters);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all details of an order")
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) throws EntityNotFound {
        return iOrderUseCase.getOrder(UUID.fromString(id));
    }

    @Operation(summary = "Get the order's history with all status changes")
    @GetMapping("/{id}/history")
    public List<OrderHistory> getOrderHistory(@PathVariable String id) throws EntityNotFound {
        return iOrderUseCase.getOrderHistory(UUID.fromString(id));
    }

    @Operation(summary = "Create a new Order")
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTO request)
    {
        var order = iOrderUseCase.createOrder(request);

        if(order == null) return ResponseEntity.badRequest().body(null);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(order);
    }

    @Operation(summary = "Update the oder's status")
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
