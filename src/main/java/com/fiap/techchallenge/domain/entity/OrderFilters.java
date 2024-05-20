package com.fiap.techchallenge.domain.entity;

import com.fiap.techchallenge.domain.enums.OrderSortFields;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import com.fiap.techchallenge.domain.enums.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilters {

    private OrderStatus status;
    private OrderSortFields orderBy;
    private SortDirection direction;
    private Boolean expandProducts;

}