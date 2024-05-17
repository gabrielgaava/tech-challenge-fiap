package com.fiap.techchallenge.adapters.in.rest.handler;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GalegaException extends Exception {

    private String customCode;

    public GalegaException(GalegaExceptionCodes code){
        super(code.toString());
        this.customCode = code.toString();

    }


}

