package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;

public interface ICreatePaymentUseCase {

  Payment execute(Payment payment);

}
