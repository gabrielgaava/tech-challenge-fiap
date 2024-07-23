package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;

public interface IUpdatePaymentUseCase {

  Payment execute(Payment payment);

}
