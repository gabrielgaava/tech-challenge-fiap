package com.fiap.techchallenge.adapters.in.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fiap.techchallenge.adapters.in.rest.constants.FieldValidationConstants.NOT_EMPTY;
import static com.fiap.techchallenge.adapters.in.rest.constants.FieldValidationConstants.NOT_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDTO {

  @NotNull(message = NOT_NULL)
  @Size(min = 11, max = 11, message = "must have 11 characters")
  private String cpf;

  @NotNull(message = NOT_NULL)
  @NotEmpty(message = NOT_EMPTY)
  private String name;

  @NotNull(message = NOT_NULL)
  @NotEmpty(message = NOT_EMPTY)
  private String email;

}
