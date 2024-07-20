package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.adapters.in.rest.dto.*;
import com.fiap.techchallenge.adapters.in.rest.mapper.OrderMapper;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderFilters;
import com.fiap.techchallenge.domain.order.usecase.*;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.order.OrderSortFields;
import com.fiap.techchallenge.domain.order.OrderStatus;
import com.fiap.techchallenge.domain.order.SortDirection;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.MercadoPagoUnavailableException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.exception.OrderNotReadyException;
import com.fiap.techchallenge.domain.payment.PaymentRepositoryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Order Controller")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final IGetOrderUseCase getOrderUseCase;
    private final IListOrdersWithFiltersUseCase listOrdersWithFiltersUseCase;
    private final IUpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final ICheckoutOrderUseCase checkoutOrderUseCase;
    private final IGetOrderHistoryUseCase getOrderHistoryUseCase;
    private final ICreateOrderUseCase createOrderUseCase;

    public OrderController(
       IGetOrderUseCase getOrderUseCase,
       IListOrdersWithFiltersUseCase listOrdersWithFiltersUseCase,
       IUpdateOrderStatusUseCase updateOrderStatusUseCase,
       ICheckoutOrderUseCase checkoutOrderUseCase,
       IGetOrderHistoryUseCase getOrderHistoryUseCase,
       ICreateOrderUseCase createOrderUseCase
    ) {
        this.getOrderUseCase = getOrderUseCase;
        this.listOrdersWithFiltersUseCase = listOrdersWithFiltersUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.checkoutOrderUseCase = checkoutOrderUseCase;
        this.getOrderHistoryUseCase = getOrderHistoryUseCase;
        this.createOrderUseCase = createOrderUseCase;
    }

    @Operation(
      summary = "List all orders based on query filters",
      parameters = {
        @Parameter(name = "status", schema = @Schema(implementation = OrderStatus.class)),
        @Parameter(name = "orderBy", schema = @Schema(implementation = OrderSortFields.class)),
        @Parameter(name = "orderDirection", schema = @Schema(implementation = SortDirection.class)),
    })
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders(
        @Valid @RequestParam(required = false) String status,
        @Valid @RequestParam(required = false) String orderBy,
        @Valid @RequestParam(required = false) String orderDirection
    )
    {
        OrderFilters filters = new OrderFilters();
        filters.setStatus(OrderStatus.fromString(status));
        filters.setOrderBy(OrderSortFields.fromString(orderBy));
        filters.setDirection(SortDirection.fromString(orderDirection));

        List<OrderDTO> ordersDTO = listOrdersWithFiltersUseCase.execute(filters)
            .stream()
            .map(OrderDTO::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(ordersDTO);
    }

    @Operation(summary = "Get all details of an order")
    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable String id) throws EntityNotFoundException
    {
        var order = getOrderUseCase.execute(UUID.fromString(id));
        return new OrderDTO(order);
    }

    @Operation(summary = "Get the order's history with all status changes")
    @GetMapping("/{id}/history")
    public List<OrderHistoryDTO> getOrderHistory(@PathVariable UUID id) throws EntityNotFoundException
    {
        return getOrderHistoryUseCase.execute(id)
            .stream()
            .map(OrderHistoryDTO::new)
            .toList();
    }

    @Operation(summary = "Create a new Order")
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderDTO request) throws EntityNotFoundException
    {
        Order order = OrderMapper.toDomain(request);
        Order createdOrder = createOrderUseCase.execute(order);

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
    ) throws OrderAlreadyWithStatusException, EntityNotFoundException {
        var oderId = UUID.fromString(id);
        var status = OrderStatus.fromString(request.getStatus().toUpperCase());
        boolean updated = updateOrderStatusUseCase.execute(oderId, status);

        if(updated) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Pay the order with mercado pago")
    @PostMapping("/{order_id}/checkout")
    public ResponseEntity<PaymentDTO> payOrder(@PathVariable("order_id") UUID orderId)
        throws EntityNotFoundException, OrderNotReadyException, MercadoPagoUnavailableException
    {
        Payment payment = checkoutOrderUseCase.execute(orderId);

        if(payment == null)
            return ResponseEntity.badRequest().body(null);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new PaymentDTO(payment));

    }


}
