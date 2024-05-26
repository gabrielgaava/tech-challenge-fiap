package com.fiap.techchallenge.adapters.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.techchallenge.domain.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    private UUID id;
    private String cpf;
    private String name;
    private String email;
}
