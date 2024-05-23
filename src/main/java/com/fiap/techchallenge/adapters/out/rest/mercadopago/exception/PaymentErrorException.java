package com.fiap.techchallenge.adapters.out.rest.mercadopago.exception;

public class PaymentErrorException extends Exception {

  public PaymentErrorException(String orderId) {
    super("Error in the attempt to pay with Mercado Pago for order " + orderId);
  }

}
