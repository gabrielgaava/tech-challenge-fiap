package com.fiap.techchallenge.adapters.in.rest.handler;


import com.fiap.techchallenge.adapters.in.rest.dto.ErrorDTO;
import com.fiap.techchallenge.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException exception) {
        ErrorDTO errorResponse = new ErrorDTO("Parameters or request body is invalid");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(OrderAlreadyWithStatusException.class)
    public ResponseEntity<ErrorDTO> handleOrderAlreadyWithStatus(OrderAlreadyWithStatusException exception) {
        ErrorDTO errorResponse = new ErrorDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleOrderAlreadyWithStatus(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MandatoryFieldException.class)
    public ResponseEntity<ErrorDTO> handleMandatoryField(MandatoryFieldException exception) {
        ErrorDTO errorResponse = new ErrorDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(OrderNotReadyException.class)
    public ResponseEntity<ErrorDTO> handleOrderNotReady(OrderNotReadyException exception) {
        ErrorDTO errorResponse = new ErrorDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<ErrorDTO> handleInvalidCpf(InvalidCpfException exception) {
        ErrorDTO errorResponse = new ErrorDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }


}
