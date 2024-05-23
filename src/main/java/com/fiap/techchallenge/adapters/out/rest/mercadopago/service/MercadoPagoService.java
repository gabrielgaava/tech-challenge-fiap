package com.fiap.techchallenge.adapters.out.rest.mercadopago.service;

import com.fiap.techchallenge.adapters.out.rest.mercadopago.dto.MercadoPagoPaymentResponseDTO;
import com.fiap.techchallenge.adapters.out.rest.mercadopago.exception.PaymentErrorException;
import com.fiap.techchallenge.domain.entity.Payment;
import com.fiap.techchallenge.domain.usecase.PaymentUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MercadoPagoService implements PaymentUseCase {

  private final String SERVICE_BASE_URI = "https://jsonplaceholder.typicode.com";

  private final RestTemplate restTemplate;

  public MercadoPagoService() {
    this.restTemplate = new RestTemplate();
  }

  @Override
  public Payment executePayment(String orderId, BigDecimal amount) throws PaymentErrorException {
    String URI = SERVICE_BASE_URI + "/todos/1/";
    MercadoPagoPaymentResponseDTO response = restTemplate.getForObject(URI, MercadoPagoPaymentResponseDTO.class);

    if(response == null){
      throw new PaymentErrorException(orderId);
    }

    Payment payment = new Payment();
    payment.setPaymentId(UUID.randomUUID());
    payment.setAmount(amount);
    payment.setPayedAt(LocalDateTime.now());
    payment.setOrderId(UUID.fromString(orderId));

    return payment;

  }

}
