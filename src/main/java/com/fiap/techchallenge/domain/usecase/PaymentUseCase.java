package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.adapters.out.rest.mercadopago.exception.PaymentErrorException;
import com.fiap.techchallenge.domain.entity.Payment;

import java.math.BigDecimal;

public interface PaymentUseCase {

  Payment executePayment(String orderId, BigDecimal amount) throws PaymentErrorException;

}
