package com.fiap.techchallenge.adapters.out.rest.mercadopago.exception;

public class PaymentErrorException extends Exception {

  public PaymentErrorException(String orderId, String gateway) {
    super("Error in the attempt to pay with " + gateway + " for order " + orderId);
  }

}
