package com.fiap.techchallenge.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
  private LocalDateTime payedAt;
  private BigDecimal amount;
  private UUID paymentId;
  private UUID orderId;

  public Payment() {}

  public Payment(LocalDateTime payedAt, BigDecimal amount, UUID paymentId, UUID orderId) {
    this.payedAt = payedAt;
    this.amount = amount;
    this.paymentId = paymentId;
    this.orderId = orderId;
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

  public UUID getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(UUID paymentId) {
    this.paymentId = paymentId;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public void setOrderId(UUID orderId) {
    this.orderId = orderId;
  }
}