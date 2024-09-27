package com.fiap.techchallenge.handlers.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthCheckDTO implements Serializable {

  private String status;
  private String message;

}
