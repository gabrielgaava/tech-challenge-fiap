package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderStatus;
import com.fiap.techchallenge.domain.order.usecase.GetOrderUseCase;
import com.fiap.techchallenge.domain.order.usecase.UpdateOrderStatusUseCase;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.gateway.OrderGateway;
import com.fiap.techchallenge.gateway.PaymentGateway;
import com.fiap.techchallenge.domain.payment.PaymentStatus;
import com.fiap.techchallenge.handlers.rest.exceptions.PaymentErrorException;

import java.time.LocalDateTime;

public class HandleExternalPaymentUseCase {

  private final PaymentGateway paymentGateway;
  private final GetOrderUseCase getOrderUseCase;
  private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

  public HandleExternalPaymentUseCase(
      PaymentGateway paymentGateway,
      GetOrderUseCase getOrderUseCase,
      UpdateOrderStatusUseCase updateOrderStatusUseCase
  ) {
    this.paymentGateway = paymentGateway;
    this.getOrderUseCase = getOrderUseCase;
    this.updateOrderStatusUseCase = updateOrderStatusUseCase;
  }

  public Payment execute(String externalPaymentId, boolean success, OrderGateway orderGateway) throws PaymentErrorException {

    Payment payment = paymentGateway.getByExternalId(externalPaymentId);

    try
    {
      Order order = getOrderUseCase.execute(payment.getOrderId(), orderGateway);
      PaymentStatus paymentStatus = success ? PaymentStatus.APPROVED : PaymentStatus.REFUSED;
      OrderStatus orderStatus = success ? OrderStatus.IN_PREPARATION : order.getStatus();

      payment.setStatus(paymentStatus.toString());

      if(success) {
        payment.setPayedAt(LocalDateTime.now());
        updateOrderStatusUseCase.execute(order.getId(), orderStatus, orderGateway);
      }

      paymentGateway.update(payment);
      return payment;
    }

    catch (EntityNotFoundException | OrderAlreadyWithStatusException e) {
      throw new PaymentErrorException(payment.getOrderId().toString(), payment.getGateway());
    }

  }

}
