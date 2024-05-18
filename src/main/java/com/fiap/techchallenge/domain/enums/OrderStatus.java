package com.fiap.techchallenge.domain.enums;

public enum OrderStatus {

    RECEIVED ("RECEIVED"),
    PAID ("PAID"),
    IN_PREPARATION ("IN_PREPARATION"),
    READY_TO_DELIVERY ("READY_TO_DELIVERY"),
    FINISHED ("FINISHED");

    private final String status;

    OrderStatus(String status){
        this.status = status;
    }

    public static OrderStatus fromString(String status){
        for(OrderStatus orderStatus : values()){
            if(orderStatus.status.equals(status))
                return orderStatus;
        }

        return null;
    }

    public String toString() {
        return this.status;
    }
}
