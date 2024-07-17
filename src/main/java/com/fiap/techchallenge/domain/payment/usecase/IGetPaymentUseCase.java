package com.fiap.techchallenge.domain.payment.usecase;

import com.fiap.techchallenge.domain.payment.Payment;

import java.util.UUID;

public interface IGetPaymentUseCase {

  Payment execute(UUID id);

  Payment execute(String externalId);

}
