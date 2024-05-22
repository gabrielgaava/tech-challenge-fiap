package com.fiap.techchallenge.adapters.in.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {

    @NotNull
    @Size(min = 36, max = 36)
    private String id;

    @NotNull
    @Min(value = 1, message = "Quantity must be greater than zero")
    @Max(value = Integer.MAX_VALUE)
    private Integer quantity;

}
