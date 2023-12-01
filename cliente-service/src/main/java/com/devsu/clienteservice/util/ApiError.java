package com.devsu.clienteservice.util;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public class ApiError {

    private String mensaje;

    public ApiError(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
