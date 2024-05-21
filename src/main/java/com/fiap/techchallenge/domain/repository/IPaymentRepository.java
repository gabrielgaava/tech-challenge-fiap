package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.entity.Payment;

public interface IPaymentRepository {

  int create(Payment payment);

}
