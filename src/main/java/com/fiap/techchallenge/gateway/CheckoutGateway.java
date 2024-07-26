package com.fiap.techchallenge.gateway;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.handlers.rest.exceptions.PaymentErrorException;

public interface CheckoutGateway {

  Payment checkoutOrder(Order order, Customer customer) throws PaymentErrorException;

}
