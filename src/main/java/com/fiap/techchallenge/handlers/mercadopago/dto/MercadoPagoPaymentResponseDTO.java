package com.fiap.techchallenge.handlers.mercadopago.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoPaymentResponseDTO {

  private String userId;
  private String id;
  private String title;
  private Boolean completed;

}
