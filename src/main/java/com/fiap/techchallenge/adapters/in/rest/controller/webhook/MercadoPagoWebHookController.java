package com.fiap.techchallenge.adapters.in.rest.controller.webhook;

import com.fiap.techchallenge.domain.service.CustomerService;
import com.fiap.techchallenge.domain.usecase.ICustomerUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@Tag(name = "Webhook Mercado Pago Controller")
@RestController
@RequestMapping("/hooks/mercadopago")
public class MercadoPagoWebHookController {

    private final ICustomerUseCase customerService;

    public MercadoPagoWebHookController(DataSource dataSource) {
        this.customerService = new CustomerService(dataSource);
    }




}
