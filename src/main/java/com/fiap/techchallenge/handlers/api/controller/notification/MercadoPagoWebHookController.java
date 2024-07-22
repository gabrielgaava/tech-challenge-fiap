package com.fiap.techchallenge.handlers.api.controller.notification;

import com.fiap.techchallenge.handlers.mercadopago.exception.PaymentErrorException;
import com.fiap.techchallenge.handlers.mercadopago.service.MercadoPagoCheckoutAdapter;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.usecase.IHandleExternalPaymentUseCase;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Webhook Mercado Pago Controller")
@RestController
@RequestMapping("/notifications/mercadopago")
public class MercadoPagoWebHookController {

    private final MercadoPagoCheckoutAdapter mercadoPagoCheckoutAdapter;
    private final IHandleExternalPaymentUseCase handleExternalPaymentUseCase;

    public MercadoPagoWebHookController(MercadoPagoCheckoutAdapter mercadoPagoCheckoutAdapter, IHandleExternalPaymentUseCase handleExternalPaymentUseCase) {
        this.mercadoPagoCheckoutAdapter = mercadoPagoCheckoutAdapter;
        this.handleExternalPaymentUseCase = handleExternalPaymentUseCase;
    }

    @PostMapping
    public ResponseEntity<?> instantPaymentNotification(@RequestParam String topic, @RequestParam String id)  {

        if(topic.equals("payment"))
        {
            try {
                // TODO: Process should be queued ?
                mercadoPagoCheckoutAdapter.checkPaymentUpdate(id);
            }

            catch (MPException | MPApiException e) {
                // Mercado Pago Service unavailable, should do a internal retry
                return ResponseEntity.ok().build();
            }

            catch (PaymentErrorException | EntityNotFoundException | OrderAlreadyWithStatusException e) {
                // SQL Internal Error
                return ResponseEntity.ok().build();
            }
        }

        // Must return 200 for mercadopago request
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Simulate the webhook integration, when 'approve' is true, simulates the success of the payment",
        parameters = {
            @Parameter(name = "approve", schema = @Schema(implementation = Boolean.class)),
            @Parameter(name = "id", schema = @Schema(implementation = String.class)),
    })
    @PostMapping("/fake")
    public ResponseEntity<?> fakePaymentNotification(@RequestParam Boolean approve, @RequestParam String id) throws PaymentErrorException {

        Payment payment = handleExternalPaymentUseCase.execute(id, true);
        return ResponseEntity.ok(payment);

    }


}
