package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.handlers.mercadopago.exception.PaymentErrorException;
import com.fiap.techchallenge.domain.payment.Payment;

public interface IHandleExternalPaymentUseCase {

  Payment execute(String externalPaymentId, boolean success) throws PaymentErrorException;

}
