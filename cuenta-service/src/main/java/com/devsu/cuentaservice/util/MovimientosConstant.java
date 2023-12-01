package com.devsu.cuentaservice.util;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public class MovimientosConstant {

    public static final String MOVIMIENTO_NO_EXISTE_MESSAGE_ERROR
            = "No se encontró el movimiento: %s - %s";
    public static final String MOVIMIENTO_NO_ENCONTRADO_MESSAGE_ERROR
            = "Movimiento con ID %s no encontrado.";
    public static final String MOVIMIENTO_NO_ELIMINADO_MESSAGE_ERROR
            = MOVIMIENTO_NO_ENCONTRADO_MESSAGE_ERROR + " No se pudo eliminar.";
    public static final String SALDO_NO_DISPONIBLE_MESSAGE_ERROR
            = "Saldo no disponible.";
    public static final String LIMITE_DIARIO_ALCANZADO_MESSAGE_ERROR
            = "Cupo diario excedido.";
    public static final String CUENTA_ASOCIADA_NO_ENCONTRADA_MESSAGE_ERROR
            = "Cuenta asociada al movimiento no encontrada.";
}
