package com.devsu.cuentaservice.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovimientosReporteResponse {

    private String cliente;
    private List<Cuenta> cuentas;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class Cuenta {

        private String numeroCuenta;
        private String tipoCuenta;
        private boolean estado;
        private List<Movimiento> movimientos;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class Movimiento {

        private String fecha;
        private BigDecimal saldoInicial;
        private BigDecimal movimiento;
        private BigDecimal saldoDisponible;
    }

}
