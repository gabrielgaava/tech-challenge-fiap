package com.fiap.techchallenge.handlers.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO {

    private String message;
    private String details;

    public ErrorDTO(String message) {
        this.message = message;
    }

    public ErrorDTO(String message, String details) {
        this.message = message;
        this.details = details;
    }

}
