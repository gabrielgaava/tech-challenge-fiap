package com.fiap.techchallenge.adapters.out.rest.service;

import com.fiap.techchallenge.adapters.out.rest.dto.MercadoPagoPaymentResponseDTO;
import com.fiap.techchallenge.adapters.out.rest.exception.PaymentErrorException;
import com.fiap.techchallenge.domain.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MercadoPagoService {

  private final String SERVICE_BASE_URI = "https://jsonplaceholder.typicode.com";

  private final RestTemplate restTemplate;

  public MercadoPagoService() {
    this.restTemplate = new RestTemplate();
  }

  public Payment pixPayment(String orderId, BigDecimal amount) throws PaymentErrorException {
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
