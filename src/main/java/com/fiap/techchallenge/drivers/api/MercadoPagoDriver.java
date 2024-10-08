package com.fiap.techchallenge.drivers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.techchallenge.domain.customer.Customer;
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
import com.mercadopago.resources.payment.PaymentStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MercadoPagoDriver implements CheckoutGateway {
    private final HandleExternalPaymentUseCase handleExternalPaymentUseCase;
    private final String GATEWAY_NAME = "MERCADOPAGO";
    private final OrderGateway orderGateway;

    public MercadoPagoDriver(HandleExternalPaymentUseCase handleExternalPaymentUseCase,
                             OrderPostgreDriver postgreDriver) {
        this.handleExternalPaymentUseCase = handleExternalPaymentUseCase;
        this.orderGateway = postgreDriver;
    }

    @Override
    public Payment checkoutOrder(Order order, Customer customer) throws PaymentErrorException {

        String ACCESS_TOKEN = "TEST-4517379499194350-070720-7a234218d8d1d7fe49f0b99f6e21902c-449901113";
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
        PaymentClient client = new PaymentClient();

        MPRequestOptions requestHeaders = this.getMPRequestOptions(order);
        PaymentCreateRequest request = this.createPaymentRequest(order, customer);

        try {
            ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .build();

            System.out.println("Request: " + mapper.writeValueAsString(request));
        }

        catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

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

        catch (MPException e) {
            System.out.println(e.getMessage());
            throw new PaymentErrorException(order.getId().toString(), GATEWAY_NAME);
        }

        catch (MPApiException e) {
            var response = e.getApiResponse();
            System.out.println(response.getContent());
            throw new PaymentErrorException(order.getId().toString(), GATEWAY_NAME);
        }

    }

    @Override
    public void checkPaymentUpdate(String paymentId, CheckoutGateway mercadoPagoGateway) throws MPException, MPApiException, PaymentErrorException {

        var client = new PaymentClient();
        var response = client.get(Long.valueOf(paymentId));
        boolean approved = response.getStatus().equals(PaymentStatus.APPROVED);

        this.handleExternalPaymentUseCase.execute(paymentId, approved, this.orderGateway);
    }

    @Override
    public Payment paymentNotification(String id, CheckoutGateway mercadoPagoGateway) throws PaymentErrorException {
        return handleExternalPaymentUseCase.execute(id, true, orderGateway);
    }

    private MPRequestOptions getMPRequestOptions(Order order) {
        Map<String, String> customHeaders = new HashMap<>();
        System.out.println("Order ID > " + order.getId().toString());
        customHeaders.put("x-idempotency-key", order.getId().toString());

        return MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();
    }

    private PaymentCreateRequest createPaymentRequest(Order order, Customer customer) {
        PaymentPayerRequest payer = null;

        if(customer != null) {
            String[] names = customer.getName().split(" ");
            String firstName = names[0];
            String lastName = names[names.length - 1];

            payer = PaymentPayerRequest.builder()
                    .email(customer.getEmail())
                    .firstName(firstName)
                    .lastName(lastName)
                    .identification(IdentificationRequest.builder().type("CPF").number(customer.getCpf()).build())
                    .build();
        }

        return PaymentCreateRequest.builder()
                .transactionAmount(new BigDecimal("100"))
                .description("Pedido Galegaburger " + order.getId().toString())
                .paymentMethodId("pix")
                .dateOfExpiration(OffsetDateTime.now().plusHours(24))
                .payer(payer)
                .build();

    }
}
