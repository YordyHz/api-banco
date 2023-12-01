package com.devsu.cuentaservice.enums;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public enum TipoMovimiento {
    D("Débito"),
    C("Crédito");

    private final String descripcion;

    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static String getDescripcion(String name) {
        for (TipoMovimiento tipoMovimiento : TipoMovimiento.values()) {
            if (tipoMovimiento.name().equals(name)) {
                return tipoMovimiento.getDescripcion();
            }
        }
        return name;
    }
}
