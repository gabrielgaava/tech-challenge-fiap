package com.fiap.techchallenge.handlers.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fiap.techchallenge.handlers.api.constants.FieldValidationConstants.NOT_BLANK;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PutCustomerDTO {

    @NotBlank(message = NOT_BLANK)
    private String name;

    @Email(message = "is not valid")
    @NotBlank(message = NOT_BLANK)
    private String email;

}
