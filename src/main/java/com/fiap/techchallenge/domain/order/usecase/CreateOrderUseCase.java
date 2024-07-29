package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.gateway.CustomerGateway;
import com.fiap.techchallenge.gateway.OrderGateway;
import com.fiap.techchallenge.domain.product.ProductAndQuantity;
import com.fiap.techchallenge.gateway.ProductGateway;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.fiap.techchallenge.domain.order.OrderStatus.CREATED;
import static java.math.RoundingMode.HALF_EVEN;

public class CreateOrderUseCase  {

  private final OrderGateway orderGateway;
  private final ProductGateway productGateway;
  private final CustomerGateway customerGateway;

  public CreateOrderUseCase(OrderGateway orderGateway, ProductGateway productGateway, CustomerGateway customerGateway) {
    this.orderGateway = orderGateway;
    this.productGateway = productGateway;
    this.customerGateway = customerGateway;
  }

  public Order execute(Order order, OrderGateway orderGateway) throws EntityNotFoundException {

    // Must be a valid and existent customer
    if(order.getCustomerId() != null) {
      Customer customer = customerGateway.getByID(order.getCustomerId());

      if(customer == null) {
        throw new EntityNotFoundException("Customer", order.getCustomerId());
      }
    }

    // The order must have at least one product to be created
    if (order.getProducts() == null || order.getProducts().isEmpty())
      throw new IllegalArgumentException("Order must have at least one product");

    // Retrieve all Products from database from ID's list to verify if it exists
    for (ProductAndQuantity item : order.getProducts()) {

      if (item.getQuantity() <= 0)
        throw new IllegalArgumentException("Quantity must be greater than zero");

      var product = productGateway.getById(item.getProduct().getId());

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
    if (orderGateway.create(order) == 1)
      return order;

      // Error trying to storage data
    else return null;

  }

}
