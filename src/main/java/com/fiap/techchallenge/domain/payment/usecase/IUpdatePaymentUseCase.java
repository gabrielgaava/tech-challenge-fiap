package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;

import java.util.List;

public interface IUpdatePaymentUseCase {

  Payment execute(Payment payment);

}
