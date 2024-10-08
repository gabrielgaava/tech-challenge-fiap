package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.MercadoPagoUnavailableException;
import com.fiap.techchallenge.domain.exception.OrderNotReadyException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.gateway.OrderGateway;
import com.fiap.techchallenge.gateway.CheckoutGateway;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;
import com.fiap.techchallenge.handlers.rest.exceptions.PaymentErrorException;

import java.util.UUID;

import static com.fiap.techchallenge.domain.order.OrderStatus.CREATED;
import static com.fiap.techchallenge.domain.order.OrderStatus.RECEIVED;

public class CheckoutOrderUseCase {

  private final OrderGateway orderGateway;
  private final CustomerGateway customerGateway;
  private final PaymentGateway paymentGateway;
  private final CheckoutGateway checkoutGateway;

  public CheckoutOrderUseCase(OrderGateway orderGateway, CustomerGateway customerGateway, PaymentGateway paymentGateway, CheckoutGateway checkoutGateway) {
    this.orderGateway = orderGateway;
    this.customerGateway = customerGateway;
    this.paymentGateway = paymentGateway;
    this.checkoutGateway = checkoutGateway;
  }

  public Payment execute(UUID id, OrderGateway orderGateway) throws EntityNotFoundException, OrderNotReadyException, MercadoPagoUnavailableException {
    Order order = orderGateway.getById(id);
    Customer customer = null;

    if (order == null || order.getStatus() == null) {
      throw new EntityNotFoundException("Order", id);
    }

    if (order.getStatus() != CREATED) {

      if (order.getStatus() == RECEIVED) {
        throw new OrderNotReadyException("Order already PAID");
      }

      throw new OrderNotReadyException("Order must be with 'RECEIVED' status");
    }

    if (order.getCustomerId() != null) {
      customer = customerGateway.getByID(order.getCustomerId());
    }

    try {
      Payment payment = checkoutGateway.checkoutOrder(order, customer);
      orderGateway.updateStatus(order, RECEIVED, order.getStatus());
      paymentGateway.create(payment);
      return payment;
    } catch (PaymentErrorException e) {
      System.out.println(e.getMessage());
      throw new MercadoPagoUnavailableException();
    }

  }

}
