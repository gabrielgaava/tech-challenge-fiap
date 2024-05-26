package com.fiap.techchallenge.adapters.in.rest.mapper;

import com.fiap.techchallenge.adapters.in.rest.dto.CustomerDTO;
import com.fiap.techchallenge.domain.entity.Customer;

public abstract class CustomerMapper {

  public static CustomerDTO fromDomain(final Customer customer) {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setId(customer.getId());
    customerDTO.setCpf(customer.getCpf());
    customerDTO.setName(customer.getName());
    customerDTO.setEmail(customer.getEmail());
    return  customerDTO;
  }

}
