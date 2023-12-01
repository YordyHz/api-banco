package com.devsu.cuentaservice.exception;

import com.devsu.cuentaservice.util.ApiError;
import com.devsu.cuentaservice.util.GeneralLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final GeneralLogger generalLogger = new GeneralLogger();

    @ExceptionHandler(CuentaException.class)
    public ResponseEntity<ApiError> handleCuentaException(CuentaException ex) {
        ApiError apiError = new ApiError(ex.getReason());
        generalLogger.logError("Error no controlado en cuentas: " + ex.getMessage());
        return new ResponseEntity<>(apiError, ex.getStatusCode());
    }

    @ExceptionHandler(MovimientosException.class)
    public ResponseEntity<ApiError> handleMovimientosException(MovimientosException ex) {
        ApiError apiError = new ApiError(ex.getReason());
        generalLogger.logError("Error no controlado en movimientos: " + ex.getMessage());
        return new ResponseEntity<>(apiError, ex.getStatusCode());
    }

}
