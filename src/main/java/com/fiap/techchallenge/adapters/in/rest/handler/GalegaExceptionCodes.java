package com.fiap.techchallenge.adapters.in.rest.handler;

import org.springframework.util.StringUtils;

public enum GalegaExceptionCodes{
    GAG_0 ("Unknow error"),
    GAG_1 ("Parameters or request body is invalid"),
    GAG_2 ("Order not found"),
    GAG_3 ("Order is not ready to be paid");

    private final String value;

    GalegaExceptionCodes(String status){
        this.value = status;
    }

    public static GalegaExceptionCodes fromString(String status){
        if(!StringUtils.hasText(status)) return null;

        for(GalegaExceptionCodes code : values()){
            if(code.value.equals(status))
                return code;
        }

        return null;
    }

    public String toString() {
        return this.value;
    }
}