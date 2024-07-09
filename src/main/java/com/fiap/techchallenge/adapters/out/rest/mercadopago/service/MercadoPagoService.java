package com.fiap.techchallenge.adapters.out.rest.mercadopago.service;

import com.fiap.techchallenge.adapters.out.rest.mercadopago.exception.PaymentErrorException;
import com.fiap.techchallenge.domain.entity.Customer;
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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class MercadoPagoService implements ICheckoutUseCase {

  public MercadoPagoService() {}

  @Override
  public Payment execute(Order order, Customer customer) throws PaymentErrorException, MPException, MPApiException {

    String ACCESS_TOKEN = "TEST-4517379499194350-070720-7a234218d8d1d7fe49f0b99f6e21902c-449901113";
    MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
    PaymentClient client = new PaymentClient();

    MPRequestOptions requestHeaders = this.getMPRequestOptions(order);
    PaymentCreateRequest request = this.createPaymentRequest(order, customer);

    var response = client.create(request, requestHeaders);

    if(response == null){
      throw new PaymentErrorException(order.getId().toString());
    }

    Payment payment = new Payment();
    payment.setGateway("MERCADOPAGO");
    payment.setPaymentId(String.valueOf(response.getId()));
    payment.setAmount(order.getAmount());
    payment.setPayedAt(null);
    payment.setOrderId(order.getId());
    payment.setTransactionData(response.getPointOfInteraction().getTransactionData());

    return payment;

  }

  private MPRequestOptions getMPRequestOptions(Order order) {
    Map<String, String> customHeaders = new HashMap<>();
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
