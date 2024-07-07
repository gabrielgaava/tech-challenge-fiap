package com.fiap.techchallenge.adapters.out.rest.mercadopago.service;

import com.fiap.techchallenge.adapters.out.rest.mercadopago.dto.MercadoPagoPaymentResponseDTO;
import com.fiap.techchallenge.adapters.out.rest.mercadopago.exception.PaymentErrorException;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.Payment;
import com.fiap.techchallenge.domain.usecase.ICheckoutUseCase;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MercadoPagoService implements ICheckoutUseCase {

  private final String SERVICE_BASE_URI = "https://jsonplaceholder.typicode.com";

  private final RestTemplate restTemplate;

  public MercadoPagoService() {
    this.restTemplate = new RestTemplate();
  }

  @Override
  public Payment execute(Order order) throws PaymentErrorException, MPException, MPApiException {

    String customerEmail = "unknow@gmail.com";
    MercadoPagoConfig.setAccessToken("APP_USR-134942421404165-070622-8c4501d1b924586a1c50eae59e15fd8d-449901113");

    PaymentClient client = new PaymentClient();

    Map<String, String> customHeaders = new HashMap<>();
    customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

    MPRequestOptions requestOptions = MPRequestOptions.builder()
            .customHeaders(customHeaders)
            .build();

    if(order.getCustomerId() != null) {
      // Retrive customer email
      customerEmail = "gabriel.gava2077@outlook.com";
    }

    PaymentCreateRequest paymentCreateRequest =
      PaymentCreateRequest.builder()
        .transactionAmount(new BigDecimal("100"))
        .description("Título do produto")
        .paymentMethodId("pix")
        .dateOfExpiration(OffsetDateTime.now().plusHours(24))
        .payer(
            PaymentPayerRequest.builder()
                    .email(customerEmail)
                    .firstName("Gabriel")
                    .identification(
                            IdentificationRequest.builder().type("CPF").number("47667846855").build())
                    .build())
        .build();

    com.mercadopago.resources.payment.Payment mpResponse = client.create(paymentCreateRequest, requestOptions);

    String URI = SERVICE_BASE_URI + "/todos/1/";
    MercadoPagoPaymentResponseDTO response = restTemplate.getForObject(URI, MercadoPagoPaymentResponseDTO.class);

    if(response == null){
      throw new PaymentErrorException(order.getId().toString());
    }

    Payment payment = new Payment();
    payment.setPaymentId(UUID.randomUUID());
    payment.setAmount(order.getAmount());
    payment.setPayedAt(LocalDateTime.now());
    payment.setOrderId(order.getId());

    return payment;

  }

}
