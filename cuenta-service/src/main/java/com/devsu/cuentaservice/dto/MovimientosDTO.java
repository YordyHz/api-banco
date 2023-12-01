package com.devsu.cuentaservice.dto;

import com.devsu.cuentaservice.enums.TipoMovimiento;
import java.math.BigDecimal;
import java.util.Date;
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
public class MovimientosDTO {

    private Long id;
    private Date fecha;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private Long CuentaId;
}
