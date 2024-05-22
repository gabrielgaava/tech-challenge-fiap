package com.fiap.techchallenge.adapters.in.rest.dto;

import com.fiap.techchallenge.domain.entity.Payment;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
	private LocalDateTime payedAt;
	private BigDecimal amount;
	private UUID paymentId;
	private UUID orderId;

	public PaymentDTO(Payment payment){
		this.payedAt = payment.getPayedAt();
		this.amount = payment.getAmount();
		this.paymentId = payment.getPaymentId();
		this.orderId = payment.getOrderId();
	}
}
