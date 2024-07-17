package com.fiap.techchallenge.domain.order.usecase.impl;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderRepositoryPort;
import com.fiap.techchallenge.domain.order.usecase.ICreateOrderUseCase;
import com.fiap.techchallenge.domain.product.ProductAndQuantity;
import com.fiap.techchallenge.domain.product.ProductRepositoryPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.fiap.techchallenge.domain.order.OrderStatus.CREATED;
import static java.math.RoundingMode.HALF_EVEN;

public class CreateOrderUseCase implements ICreateOrderUseCase {

  private final OrderRepositoryPort orderRepository;
  private final ProductRepositoryPort productRepository;

  public CreateOrderUseCase(OrderRepositoryPort orderRepository, ProductRepositoryPort productRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  public Order execute(Order order) throws EntityNotFoundException {

    // The order must have at least one product to be created
    if (order.getProducts() == null || order.getProducts().isEmpty())
      throw new IllegalArgumentException("Order must have at least one product");

    // Retrieve all Products from database from ID's list to verify if it exists
    for (ProductAndQuantity item : order.getProducts()) {

      if (item.getQuantity() <= 0)
        throw new IllegalArgumentException("Quantity must be greater than zero");

      var product = productRepository.getById(item.getProduct().getId());

      if (product == null) {
        throw new EntityNotFoundException("Product", item.getProduct().getId());
      }

      item.setProduct(product);
    }

    // Sum all products and multiply its quantities to generate the order amount
    BigDecimal orderAmount = order.getProducts()
        .stream()
        .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, HALF_EVEN);

    // Create final order object
    order.setId(UUID.randomUUID());
    order.setAmount(orderAmount);
    order.setStatus(CREATED);
    order.setCreatedAt(LocalDateTime.now());

    // Successfully created all data in database
    if (orderRepository.create(order) == 1)
      return order;

      // Error trying to storage data
    else return null;

  }

}
