package com.devsu.clienteservice.exception;

import com.devsu.clienteservice.util.GeneralLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public class ClienteException extends ResponseStatusException {

    private final GeneralLogger clienteLogger = new GeneralLogger();

    public ClienteException(HttpStatus status) {
        super(status);
        clienteLogger.logError("Excepción de cliente con status: " + status);
    }

    public ClienteException(HttpStatus status, String reason) {
        super(status, reason);
        clienteLogger.logError("Excepción de cliente: " + reason + " con status: " + status);
    }

    public ClienteException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
        clienteLogger.logError("Excepción de cliente: " + reason + " con status: " + status, cause);
    }
}
