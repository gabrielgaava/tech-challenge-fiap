package com.fiap.techchallenge.handlers.rest.exceptions;

public class PaymentErrorException extends Exception {

  public PaymentErrorException(String orderId, String gateway) {
    super("Error in the attempt to pay with " + gateway + " for order " + orderId);
  }

}
