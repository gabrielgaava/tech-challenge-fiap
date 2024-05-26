package com.fiap.techchallenge.adapters.in.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import static com.fiap.techchallenge.adapters.in.rest.constants.FieldValidationConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDTO {

  @NotNull(message = NOT_NULL)
  @CPF(message = "must contain 11 digits and be valid")
  private String cpf;

  @NotBlank(message = NOT_BLANK)
  private String name;

  @NotBlank(message = NOT_BLANK)
  @Email(message = "must be a valid email")
  private String email;

}
