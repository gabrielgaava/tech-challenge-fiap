package com.fiap.techchallenge.adapters.in.rest.dto;

import com.fiap.techchallenge.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {

    String costumerId;

    List<OrderProductDTO> products;

}
