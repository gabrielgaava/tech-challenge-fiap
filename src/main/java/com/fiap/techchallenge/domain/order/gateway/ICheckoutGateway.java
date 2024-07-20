package com.fiap.techchallenge.domain.order.gateway;

import com.fiap.techchallenge.adapters.out.rest.mercadopago.exception.PaymentErrorException;
import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.payment.Payment;

public interface ICheckoutGateway {

  Payment checkoutOrder(Order order, Customer customer) throws PaymentErrorException;

}
