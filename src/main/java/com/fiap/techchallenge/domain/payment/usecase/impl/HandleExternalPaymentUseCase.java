package com.fiap.techchallenge.domain.payment.usecase.impl;

import com.fiap.techchallenge.adapters.out.rest.mercadopago.exception.PaymentErrorException;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.order.OrderStatus;
import com.fiap.techchallenge.domain.order.usecase.impl.GetOrderUseCase;
import com.fiap.techchallenge.domain.order.usecase.impl.UpdateOrderStatusUseCase;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.PaymentRepositoryPort;
import com.fiap.techchallenge.domain.payment.PaymentStatus;
import com.fiap.techchallenge.domain.payment.usecase.IHandleExternalPaymentUseCase;

import java.time.LocalDateTime;

public class HandleExternalPaymentUseCase implements IHandleExternalPaymentUseCase {

  private final PaymentRepositoryPort paymentRepository;
  private final GetOrderUseCase getOrderUseCase;
  private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

  public HandleExternalPaymentUseCase(
      PaymentRepositoryPort paymentRepository,
      GetOrderUseCase getOrderUseCase,
      UpdateOrderStatusUseCase updateOrderStatusUseCase
  ) {
    this.paymentRepository = paymentRepository;
    this.getOrderUseCase = getOrderUseCase;
    this.updateOrderStatusUseCase = updateOrderStatusUseCase;
  }

  @Override
  public Payment execute(String externalPaymentId, boolean success) throws PaymentErrorException {

    Payment payment = paymentRepository.getByExternalId(externalPaymentId);

    try
    {
      Order order = getOrderUseCase.execute(payment.getOrderId());
      PaymentStatus paymentStatus = success ? PaymentStatus.APPROVED : PaymentStatus.REFUSED;
      OrderStatus orderStatus = success ? OrderStatus.IN_PREPARATION : order.getStatus();

      payment.setStatus(paymentStatus.toString());

      if(success) {
        payment.setPayedAt(LocalDateTime.now());
        updateOrderStatusUseCase.execute(order.getId(), orderStatus);
      }

      paymentRepository.update(payment);
      return payment;
    }

    catch (EntityNotFoundException | OrderAlreadyWithStatusException e) {
      throw new PaymentErrorException(payment.getOrderId().toString(), payment.getGateway());
    }

  }

}