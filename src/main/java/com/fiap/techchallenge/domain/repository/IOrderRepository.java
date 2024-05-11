package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository {

    List<Order> getAll();

    int create(Order order);

}
