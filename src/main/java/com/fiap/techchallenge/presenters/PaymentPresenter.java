package com.fiap.techchallenge.presenters;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.handlers.rest.dto.PaymentDTO;
import com.fiap.techchallenge.handlers.rest.dto.PaymentWebhookDTO;

public abstract class PaymentPresenter {

  public static PaymentWebhookDTO toPaymentWebhookDTO(Payment payment) {
    PaymentWebhookDTO dto = new PaymentWebhookDTO();
    dto.setPayedAt(payment.getPayedAt());
    dto.setAmount(payment.getAmount());
    dto.setGateway(payment.getGateway());
    dto.setExternalId(payment.getExternalId());
    dto.setStatus(payment.getStatus());
    dto.setPaymentId(payment.getId());
    dto.setOrderId(payment.getOrderId());

    return dto;
  }

}
