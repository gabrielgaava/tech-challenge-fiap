package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.MercadoPagoUnavailableException;
import com.fiap.techchallenge.domain.exception.OrderNotReadyException;
import com.fiap.techchallenge.domain.payment.Payment;

import java.util.UUID;

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
