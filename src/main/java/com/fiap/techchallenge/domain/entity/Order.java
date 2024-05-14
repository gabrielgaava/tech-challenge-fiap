package com.fiap.techchallenge.domain.entity;

import com.fiap.techchallenge.domain.enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private UUID id;

    private UUID costumerId;

    private String orderNumber;

    private Float amount;

    private OrderStatus status;

    private LocalDateTime createdAt;

}
