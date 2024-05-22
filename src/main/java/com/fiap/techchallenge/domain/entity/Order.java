package com.fiap.techchallenge.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    private UUID id;

    private UUID customerId;

    private Integer orderNumber;

    private BigDecimal amount;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private long waitingTimeInSeconds;

    List<ProductAndQuantity> products;

    List<OrderHistory> history;

}
