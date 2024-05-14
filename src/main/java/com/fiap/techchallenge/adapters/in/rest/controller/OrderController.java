package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.usecase.IOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
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

}
