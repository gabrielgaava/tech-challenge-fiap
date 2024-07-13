package com.fiap.techchallenge.adapters.in.rest.controller.webhook;

import com.fiap.techchallenge.adapters.out.rest.mercadopago.service.MercadoPagoService;
import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.Payment;
import com.fiap.techchallenge.domain.service.CustomerService;
import com.fiap.techchallenge.domain.service.OrderService;
import com.fiap.techchallenge.domain.usecase.ICustomerUseCase;
import com.fiap.techchallenge.domain.usecase.IOrderUseCase;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@Tag(name = "Webhook Mercado Pago Controller")
@RestController
@RequestMapping("/notifications/mercadopago")
public class MercadoPagoWebHookController {

    private final MercadoPagoService mercadoPagoService;

    public MercadoPagoWebHookController(DataSource dataSource) {
        this.mercadoPagoService = new MercadoPagoService(dataSource);
    }

    @PostMapping
    public ResponseEntity<?> instantPaymentNotification(@RequestParam String topic, @RequestParam String id) throws MPException, MPApiException {

        if(topic.equals("payment")) {
            mercadoPagoService.checkPaymentUpdate(id);
        }

        // Must return 200 for mercadopago request
        return ResponseEntity.ok().build();
    }



}
