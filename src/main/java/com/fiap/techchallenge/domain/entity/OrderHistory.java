package com.fiap.techchallenge.domain.entity;

import com.fiap.techchallenge.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {

    private OrderStatus previousStatus;
    private OrderStatus lastStatus;
    private LocalDateTime moment;

}
