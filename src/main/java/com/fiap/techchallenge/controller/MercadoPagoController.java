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
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MercadoPagoController {

    private final HandleExternalPaymentUseCase handleExternalPaymentUseCase;
    private final String GATEWAY_NAME = "MERCADOPAGO";
    private final OrderGateway orderGateway;

    public MercadoPagoController(HandleExternalPaymentUseCase handleExternalPaymentUseCase, OrderPostgreDriver postgreDriver) {
        this.handleExternalPaymentUseCase = handleExternalPaymentUseCase;
        this.orderGateway = postgreDriver;
    }

    public Payment checkoutOrder(Order order, Customer customer) throws PaymentErrorException {

        String ACCESS_TOKEN = "TEST-4517379499194350-070720-7a234218d8d1d7fe49f0b99f6e21902c-449901113";
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
        PaymentClient client = new PaymentClient();

        MPRequestOptions requestHeaders = this.getMPRequestOptions(order);
        PaymentCreateRequest request = this.createPaymentRequest(order, customer);

        try {
            var response = client.create(request, requestHeaders);

            if(response == null){
                throw new PaymentErrorException(order.getId().toString(),  GATEWAY_NAME);
            }

            Payment payment = new Payment();
            payment.setGateway(GATEWAY_NAME);
            payment.setId(UUID.randomUUID());
            payment.setExternalId(String.valueOf(response.getId()));
            payment.setStatus(com.fiap.techchallenge.domain.payment.PaymentStatus.PENDING.toString());
            payment.setAmount(order.getAmount());
            payment.setPayedAt(null);
            payment.setOrderId(order.getId());
            payment.setTransactionData(response.getPointOfInteraction().getTransactionData());

            return payment;
        }

        catch (MPException | MPApiException e) {
            throw new PaymentErrorException(order.getId().toString(), GATEWAY_NAME);
        }

    }

    public void checkPaymentUpdate(String paymentId, CheckoutGateway mercadoPagoGateway) throws MPException, MPApiException, EntityNotFoundException, OrderAlreadyWithStatusException, PaymentErrorException {
        mercadoPagoGateway.checkPaymentUpdate(paymentId);
    }

    public Payment paymentNotification(String id, CheckoutGateway mercadoPagoGateway) throws PaymentErrorException {
        return
    }


}
