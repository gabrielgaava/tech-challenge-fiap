package com.fiap.techchallenge.domain.entity;

import com.fiap.techchallenge.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
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
