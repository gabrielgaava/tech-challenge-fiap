package com.fiap.techchallenge.gateway;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.handlers.webhook.mercadopago.exception.PaymentErrorException;

public interface ICheckoutGateway {

  Payment checkoutOrder(Order order, Customer customer) throws PaymentErrorException;

}
