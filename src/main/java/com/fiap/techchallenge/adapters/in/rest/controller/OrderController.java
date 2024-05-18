package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.adapters.in.rest.handler.GalegaException;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.Payment;
import com.fiap.techchallenge.domain.usecase.IOrderUseCase;
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
    public List<Order> getOrders() {
        return iOrderUseCase.getOrders();
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTO request) {
        var order = iOrderUseCase.createOrder(request);

        if(order == null) return ResponseEntity.badRequest().body(null);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(order);
    }

    @PostMapping("/{order_number}/payment")
    public ResponseEntity<Payment> payOrder(@PathVariable("order_number") String orderNumber) throws GalegaException {
        var payment = iOrderUseCase.payOrder(orderNumber);
        if(payment == null) return ResponseEntity.badRequest().body(null);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(payment);

    }

}
