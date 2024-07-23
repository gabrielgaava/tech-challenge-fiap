package com.fiap.techchallenge.handlers.rest.api;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.usecase.IGetPaymentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment Controller")
public class PaymentAPI {

  private final IGetPaymentUseCase getPaymentUseCase;

  public PaymentAPI(IGetPaymentUseCase getPaymentUseCase) {
    this.getPaymentUseCase = getPaymentUseCase;
  }

  @Operation(
    summary = "Get a payment details by its ID. When 'isExternal' is true, search by the external gateway ID",
    parameters = {
        @Parameter(name = "isExternal", schema = @Schema(implementation = Boolean.class)),
  })
  @GetMapping("/{payment_id}")
  public ResponseEntity<Payment> getPayment(
      @PathVariable("payment_id") String paymentId,
      @Valid @RequestParam(required = false) boolean isExternal
  ) {
    if (isExternal) {
      return ResponseEntity.ok(getPaymentUseCase.execute(paymentId));
    }

    return ResponseEntity.ok(getPaymentUseCase.execute(UUID.fromString(paymentId)));
  }

}
