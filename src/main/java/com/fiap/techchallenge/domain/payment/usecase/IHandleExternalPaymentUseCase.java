package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.handlers.webhook.mercadopago.exception.PaymentErrorException;

public interface IHandleExternalPaymentUseCase {

  Payment execute(String externalPaymentId, boolean success) throws PaymentErrorException;

}
