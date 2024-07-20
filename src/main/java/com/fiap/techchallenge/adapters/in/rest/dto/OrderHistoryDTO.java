package com.fiap.techchallenge.adapters.in.rest.dto;

import com.fiap.techchallenge.domain.order.OrderHistory;
import com.fiap.techchallenge.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDTO {
	private OrderStatus previousStatus;
	private OrderStatus lastStatus;
	private LocalDateTime moment;

	public OrderHistoryDTO(OrderHistory orderHistory){
		this.previousStatus = orderHistory.getPreviousStatus();
		this.lastStatus = orderHistory.getLastStatus();
		this.moment = orderHistory.getMoment();
	}
}
