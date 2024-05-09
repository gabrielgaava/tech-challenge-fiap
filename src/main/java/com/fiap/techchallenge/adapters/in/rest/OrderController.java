package com.fiap.techchallenge.adapters.in.rest;

import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.usecase.IOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
