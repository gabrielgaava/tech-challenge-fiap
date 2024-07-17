package com.fiap.techchallenge.adapters.in.rest.mapper;

import com.fiap.techchallenge.adapters.in.rest.dto.CustomerDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.PutCustomerDTO;
import com.fiap.techchallenge.domain.customer.Customer;

public abstract class CustomerMapper {

  public static CustomerDTO fromDomain(final Customer customer) {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setId(customer.getId());
    customerDTO.setCpf(customer.getCpf());
    customerDTO.setName(customer.getName());
    customerDTO.setEmail(customer.getEmail());
    return  customerDTO;
  }

  public static Customer toDomain(final PutCustomerDTO dto, String cpf) {
    Customer customer = new Customer();
    customer.setName(dto.getName());
    customer.setEmail(dto.getEmail());
    customer.setCpf(cpf);

    return  customer;
  }

}
