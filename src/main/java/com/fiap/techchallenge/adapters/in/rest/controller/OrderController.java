package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.adapters.in.rest.dto.*;
import com.fiap.techchallenge.adapters.in.rest.mapper.OrderMapper;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.OrderFilters;
import com.fiap.techchallenge.domain.entity.Payment;
import com.fiap.techchallenge.domain.enums.OrderSortFields;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import com.fiap.techchallenge.domain.enums.SortDirection;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.MercadoPagoUnavailableException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.exception.OrderNotReadyException;
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

import java.util.ArrayList;
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
    public ResponseEntity<List<OrderDTO>> getOrders(
        @Valid @RequestParam(required = false) String status,
        @Valid @RequestParam(required = false) String orderBy,
        @Valid @RequestParam(required = false) String orderDirection,
        @Valid @RequestParam(required = false) Boolean expandProducts
    )
    {
        OrderFilters filters = new OrderFilters();
        filters.setStatus(OrderStatus.fromString(status));
        filters.setOrderBy(OrderSortFields.fromString(orderBy));
        filters.setExpandProducts(expandProducts);

        orderDirection = orderDirection == null ? "ASC" : orderDirection;
        filters.setDirection(SortDirection.fromString(orderDirection));

        var ordersDTO = new ArrayList<OrderDTO>();
        iOrderUseCase.getOrders(filters).forEach((order) ->{
            if (order != null){
                ordersDTO.add(new OrderDTO(order));
            }
        });
        return ResponseEntity.ok(ordersDTO);
    }

    @Operation(summary = "Get all details of an order")
    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable String id) throws EntityNotFoundException
    {
        var order = iOrderUseCase.getOrder(UUID.fromString(id));
        return new OrderDTO(order);
    }

    @Operation(summary = "Get the order's history with all status changes")
    @GetMapping("/{id}/history")
    public List<OrderHistoryDTO> getOrderHistory(@PathVariable String id) throws EntityNotFoundException
    {
        var orderHistoryDTO = new ArrayList<OrderHistoryDTO>();
        iOrderUseCase.getOrderHistory(UUID.fromString(id)).forEach(orderHistory -> {
            orderHistoryDTO.add(new OrderHistoryDTO(orderHistory));
        });
        return orderHistoryDTO;
    }

    @Operation(summary = "Create a new Order")
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderDTO request) throws EntityNotFoundException
    {
        Order order = OrderMapper.toDomain(request);
        Order createdOrder = iOrderUseCase.createOrder(order);

        if(createdOrder == null)
            return ResponseEntity.badRequest().body(null);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new OrderDTO(createdOrder));
    }

    @Operation(summary = "Update the oder's status")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateOrderStatusDTO request
    ) throws OrderAlreadyWithStatusException
    {
        var oderId = UUID.fromString(id);
        var status = OrderStatus.fromString(request.getStatus().toUpperCase());
        boolean updated = iOrderUseCase.updateOrderStatus(oderId, status);

        if(updated) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Pay the order with mercado pago")
    @PostMapping("/{order_id}/payment")
    public ResponseEntity<PaymentDTO> payOrder(@PathVariable("order_id") UUID orderId)
        throws EntityNotFoundException, OrderNotReadyException, MercadoPagoUnavailableException
    {
        Payment payment = iOrderUseCase.payOrder(orderId);

        if(payment == null)
            return ResponseEntity.badRequest().body(null);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new PaymentDTO(payment));

    }


}
