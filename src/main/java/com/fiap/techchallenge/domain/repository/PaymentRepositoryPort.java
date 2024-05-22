package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.entity.Payment;

public interface PaymentRepositoryPort {

  int create(Payment payment);

}
