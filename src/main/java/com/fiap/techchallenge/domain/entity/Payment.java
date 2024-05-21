package com.fiap.techchallenge.domain.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
  private LocalDateTime payedAt;
  private BigDecimal amount;
  private UUID paymentId;
  private UUID orderId;
}