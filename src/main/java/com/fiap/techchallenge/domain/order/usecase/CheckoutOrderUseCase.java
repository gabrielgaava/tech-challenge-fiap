package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.MercadoPagoUnavailableException;
import com.fiap.techchallenge.domain.exception.OrderNotReadyException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.gateway.OrderGateway;
import com.fiap.techchallenge.gateway.ICheckoutGateway;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.PaymentGateway;
import com.fiap.techchallenge.handlers.webhook.mercadopago.exception.PaymentErrorException;

import java.util.UUID;

import static com.fiap.techchallenge.domain.order.OrderStatus.CREATED;
import static com.fiap.techchallenge.domain.order.OrderStatus.RECEIVED;

public class CheckoutOrderUseCase {

  private final OrderGateway orderRepository;
  private final CustomerGateway customerRepository;
  private final PaymentGateway paymentRepository;
  private final ICheckoutGateway checkoutGateway;

  public CheckoutOrderUseCase(OrderGateway orderRepository, CustomerGateway customerRepository, PaymentGateway paymentRepository, ICheckoutGateway checkoutGateway) {
    this.orderRepository = orderRepository;;
    this.customerRepository = customerRepository;
    this.paymentRepository = paymentRepository;
    this.checkoutGateway = checkoutGateway;
  }

  public Payment execute(UUID id) throws EntityNotFoundException, OrderNotReadyException, MercadoPagoUnavailableException {
    Order order = orderRepository.getById(id);
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
      customer = customerRepository.getByID(order.getCustomerId());
    }

    try {
      Payment payment = checkoutGateway.checkoutOrder(order, customer);
      orderRepository.updateStatus(order, RECEIVED, order.getStatus());
      paymentRepository.create(payment);
      return payment;
    } catch (PaymentErrorException e) {
      throw new MercadoPagoUnavailableException();
    }

  }

}
