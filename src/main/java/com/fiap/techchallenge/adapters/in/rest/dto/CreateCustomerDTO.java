package com.fiap.techchallenge.adapters.in.rest.dto;

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
public class CreateCustomerDTO {

  @NotNull
  @Size(min = 11, max = 11)
  private String cpf;

  @NotNull
  private String name;

  @NotNull
  private String email;

}
