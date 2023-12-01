package com.devsu.cuentaservice.exception;

import com.devsu.cuentaservice.util.GeneralLogger;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public class MovimientosException extends ResponseStatusException {

    private final GeneralLogger movimientosLogger = new GeneralLogger();

    public MovimientosException(HttpStatus status) {
        super(status);
        movimientosLogger.logError("Excepción de movimientos con status: " + status);
    }

    public MovimientosException(HttpStatus status, String reason) {
        super(status, reason);
        movimientosLogger.logError("Excepción de movimientos: " + reason + " con status: " + status);
    }

    public MovimientosException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
        movimientosLogger.logError("Excepción de movimientos: " + reason + " con status: " + status, cause);
    }
}
