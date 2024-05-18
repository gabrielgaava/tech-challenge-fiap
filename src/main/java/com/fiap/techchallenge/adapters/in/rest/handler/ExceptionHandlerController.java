package com.fiap.techchallenge.adapters.in.rest.handler;


import com.fiap.techchallenge.adapters.in.rest.dto.ErrorDTO;
import com.fiap.techchallenge.domain.exception.EntityNotFound;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatus;
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

    @ExceptionHandler(OrderAlreadyWithStatus.class)
    public ResponseEntity<ErrorDTO> handleOrderAlreadyWithStatus(OrderAlreadyWithStatus exception) {
        ErrorDTO errorResponse = new ErrorDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<ErrorDTO> handleOrderAlreadyWithStatus(EntityNotFound exception) {
        return ResponseEntity.notFound().build();
    }

}
