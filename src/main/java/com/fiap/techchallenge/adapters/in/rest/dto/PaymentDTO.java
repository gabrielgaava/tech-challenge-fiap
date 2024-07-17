package com.fiap.techchallenge.adapters.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.techchallenge.domain.payment.Payment;
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
	private LocalDateTime payedAt;
	private BigDecimal amount;
	private String gateway;
	private String externalId;
	private String status;
	private Object transactionData;
	private UUID paymentId;
	private UUID orderId;

	public PaymentDTO(Payment payment){
		this.payedAt = payment.getPayedAt();
		this.amount = payment.getAmount();
		this.gateway = payment.getGateway();
		this.externalId = payment.getExternalId();
		this.status = payment.getStatus();
		this.paymentId = payment.getId();
		this.orderId = payment.getOrderId();
		this.transactionData = payment.getTransactionData();
	}
}
