package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.adapters.out.rest.mercadopago.exception.PaymentErrorException;
import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.MercadoPagoUnavailableException;
import com.fiap.techchallenge.domain.exception.OrderNotReadyException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.payment.Payment;

import java.util.UUID;

import static com.fiap.techchallenge.domain.order.OrderStatus.CREATED;
import static com.fiap.techchallenge.domain.order.OrderStatus.RECEIVED;

public interface ICheckoutOrderUseCase {

  /**
   * Make payment with fake MercadoPago Checkout
   * @param id Order ID to be paid
   * @return Payment details
   * @throws EntityNotFoundException
   * @throws OrderNotReadyException
   * @throws MercadoPagoUnavailableException
   */
  Payment execute(UUID id) throws EntityNotFoundException, OrderNotReadyException, MercadoPagoUnavailableException;

}
