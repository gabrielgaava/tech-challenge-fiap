package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.MercadoPagoUnavailableException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.exception.OrderNotReadyException;
import com.fiap.techchallenge.domain.order.*;
import com.fiap.techchallenge.domain.order.usecase.*;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.handlers.rest.dto.*;
import com.fiap.techchallenge.presenters.OrderPresenter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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



    public ResponseEntity<List<OrderDTO>> getOrders(
        String status,
        String orderBy,
        String orderDirection
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

    public OrderDTO getOrder(String id) throws EntityNotFoundException
    {
        var order = getOrderUseCase.execute(UUID.fromString(id));
        return new OrderDTO(order);
    }

    public List<OrderHistoryDTO> getOrderHistory(UUID id) throws EntityNotFoundException
    {
        return getOrderHistoryUseCase.execute(id)
            .stream()
            .map(OrderHistoryDTO::new)
            .toList();
    }


    public ResponseEntity<OrderDTO> createOrder(CreateOrderDTO request) throws EntityNotFoundException
    {
        Order order = OrderPresenter.toDomain(request);
        Order createdOrder = createOrderUseCase.execute(order);

        if(createdOrder == null)
            return ResponseEntity.badRequest().body(null);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new OrderDTO(createdOrder));
    }

    public ResponseEntity<?> updateStatus(
            String id,
            UpdateOrderStatusDTO request
    ) throws OrderAlreadyWithStatusException, EntityNotFoundException {
        var oderId = UUID.fromString(id);
        var status = OrderStatus.fromString(request.getStatus().toUpperCase());
        boolean updated = updateOrderStatusUseCase.execute(oderId, status);

        if(updated) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<PaymentDTO> payOrder(UUID orderId)
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
