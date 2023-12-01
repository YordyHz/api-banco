package com.devsu.cuentaservice.enums;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public enum TipoCuenta {
    A("Ahorros"),
    C("Corriente");

    private final String descripcion;

    TipoCuenta(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static String getDescripcion(String name) {
        for (TipoCuenta tipoCuenta : TipoCuenta.values()) {
            if (tipoCuenta.name().equals(name)) {
                return tipoCuenta.getDescripcion();
            }
        }
        return name;
    }
}
