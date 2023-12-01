package com.devsu.clienteservice.exception;

import com.devsu.clienteservice.util.ApiError;
import com.devsu.clienteservice.util.GeneralLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private GeneralLogger clienteLogger;

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<ApiError> handleEmptyInput(ClienteException ex) {
        ApiError apiError = new ApiError(ex.getReason());
        clienteLogger.logError("Error no controlado en clientes: " + ex.getMessage());
        return new ResponseEntity<>(apiError, ex.getStatusCode());
    }
}
