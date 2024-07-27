package com.fiap.techchallenge.handlers.rest.dto;

import com.fiap.techchallenge.handlers.rest.constants.FieldValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDTO {

  @NotNull(message = FieldValidationConstants.NOT_NULL)
  @CPF(message = "must contain 11 digits and be valid")
  private String cpf;

  @NotBlank(message = FieldValidationConstants.NOT_BLANK)
  private String name;

  @NotBlank(message = FieldValidationConstants.NOT_BLANK)
  @Email(message = "must be a valid email")
  private String email;

}
