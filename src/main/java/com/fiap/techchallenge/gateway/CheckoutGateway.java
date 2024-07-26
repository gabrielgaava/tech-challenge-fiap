package com.fiap.techchallenge.gateway;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.handlers.rest.exceptions.PaymentErrorException;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

public interface CheckoutGateway {

  Payment checkoutOrder(Order order, Customer customer) throws PaymentErrorException;
  void checkPaymentUpdate(String paymentId, CheckoutGateway mercadoPagoGateway) throws MPException, MPApiException, PaymentErrorException;
  Payment paymentNotification(String id, CheckoutGateway mercadoPagoGateway) throws PaymentErrorException;
}
