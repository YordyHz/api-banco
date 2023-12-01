package com.devsu.cuentaservice.exception;

import com.devsu.cuentaservice.util.GeneralLogger;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public class CuentaException extends ResponseStatusException {

    private final GeneralLogger cuentaLogger = new GeneralLogger();

    public CuentaException(HttpStatus status) {
        super(status);
        cuentaLogger.logError("Excepción de cuenta con status: " + status);
    }

    public CuentaException(HttpStatus status, String reason) {
        super(status, reason);
        cuentaLogger.logError("Excepción de cuenta: " + reason + " con status: " + status);
    }

    public CuentaException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
        cuentaLogger.logError("Excepción de cuenta: " + reason + " con status: " + status, cause);
    }
}
