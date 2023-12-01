package com.devsu.cuentaservice.dto;

import com.devsu.cuentaservice.enums.TipoCuenta;
import java.math.BigDecimal;
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
public class CuentaDTO {

    private Long id;
    private Long numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal limiteDiario;
    private boolean estado;
    private Long clienteId;
}
