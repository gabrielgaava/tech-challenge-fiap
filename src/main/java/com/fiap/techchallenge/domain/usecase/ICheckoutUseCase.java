package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.adapters.out.rest.mercadopago.exception.PaymentErrorException;
import com.fiap.techchallenge.domain.entity.Customer;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.Payment;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

public interface ICheckoutUseCase {

  Payment execute(Order order, Customer customer) throws PaymentErrorException, MPException, MPApiException;

}
