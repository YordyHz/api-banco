package com.devsu.cuentaservice.util;

import com.devsu.cuentaservice.enums.TipoMovimiento;
import java.math.BigDecimal;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public class Utilidades {

    public static TipoMovimiento tipoMovimiento(BigDecimal valor) {
        int comparacion = valor.compareTo(BigDecimal.ZERO);

        if (comparacion < 0) {
            return TipoMovimiento.D;
        } else {
            return TipoMovimiento.C;
        }
    }

    public static boolean isZeroOrLess(BigDecimal valor) {
        return valor.compareTo(BigDecimal.ZERO) <= 0;
    }
}
