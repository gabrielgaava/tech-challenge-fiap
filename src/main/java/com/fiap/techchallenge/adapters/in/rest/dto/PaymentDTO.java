package com.fiap.techchallenge.adapters.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.techchallenge.domain.entity.Payment;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {
	private BigDecimal amount;
	private Object transactionData;
	private String paymentId;
	private UUID orderId;

	public PaymentDTO(Payment payment){
		this.amount = payment.getAmount();
		this.paymentId = payment.getPaymentId();
		this.orderId = payment.getOrderId();
		this.transactionData = payment.getTransactionData();
	}
}
