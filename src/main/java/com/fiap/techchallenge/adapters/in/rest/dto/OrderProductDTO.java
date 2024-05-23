package com.fiap.techchallenge.adapters.in.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fiap.techchallenge.adapters.in.rest.constants.FieldValidationConstants.NOT_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {

    @NotNull(message = NOT_NULL)
    @Size(min = 36, max = 36, message = "must have 36 characters")
    private String id;

    @NotNull(message = NOT_NULL)
    @Min(value = 1, message = "must be greater than zero")
    @Max(value = Integer.MAX_VALUE)
    private Integer quantity;

}
