package com.fiap.techchallenge.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Payment {
  private LocalDateTime payedAt;
  private BigDecimal amount;
  private String gateway;
  private String paymentId;
  private UUID orderId;
  private Object transactionData;

  public Payment() {}

  public Payment(LocalDateTime payedAt, BigDecimal amount, String gateway, String paymentId, UUID orderId) {
    this.payedAt = payedAt;
    this.amount = amount;
    this.paymentId = paymentId;
    this.orderId = orderId;
    this.gateway = gateway;
  }

  public LocalDateTime getPayedAt() {
    return payedAt;
  }

  public void setPayedAt(LocalDateTime payedAt) {
    this.payedAt = payedAt;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public void setOrderId(UUID orderId) {
    this.orderId = orderId;
  }

  public String getGateway() {
    return gateway;
  }

  public void setGateway(String gateway) {
    this.gateway = gateway;
  }

  public Object getTransactionData() {
    return transactionData;
  }

  public void setTransactionData(Object transactionData) {
    this.transactionData = transactionData;
  }
}