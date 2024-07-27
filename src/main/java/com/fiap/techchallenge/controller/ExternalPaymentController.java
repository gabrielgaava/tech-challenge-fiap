package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.usecase.HandleExternalPaymentUseCase;
import com.fiap.techchallenge.drivers.postgresql.OrderPostgreDriver;
import com.fiap.techchallenge.gateway.CheckoutGateway;
import com.fiap.techchallenge.gateway.OrderGateway;
import com.fiap.techchallenge.handlers.rest.exceptions.PaymentErrorException;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ExternalPaymentController {

    private final HandleExternalPaymentUseCase handleExternalPaymentUseCase;
    private final String GATEWAY_NAME = "MERCADOPAGO";

    public ExternalPaymentController(HandleExternalPaymentUseCase handleExternalPaymentUseCase) {
        this.handleExternalPaymentUseCase = handleExternalPaymentUseCase;
    }

    public void checkPaymentUpdate(String paymentId, CheckoutGateway checkoutGateway) throws MPException, MPApiException, EntityNotFoundException, OrderAlreadyWithStatusException, PaymentErrorException {
        checkoutGateway.checkPaymentUpdate(paymentId, checkoutGateway);
    }

    public Payment paymentNotification(String id, CheckoutGateway checkoutGateway) throws PaymentErrorException {
        return checkoutGateway.paymentNotification(id, checkoutGateway);
    }


}
