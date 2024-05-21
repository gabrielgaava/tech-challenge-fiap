package com.fiap.techchallenge.domain.exception;

public class OrderNotReadyException extends Exception{

  public OrderNotReadyException() {
    super("Order is not ready to be paid");
  }

}
