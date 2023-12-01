package com.devsu.clienteservice.enums;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public enum Genero {
    M("Masculino"),
    F("Femenino"),
    O("Otro");

    private final String descripcion;

    Genero(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static String getDescripcion(String name) {
        for (Genero genero : Genero.values()) {
            if (genero.name().equals(name)) {
                return genero.getDescripcion();
            }
        }
        return name;
    }
}
