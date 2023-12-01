package com.devsu.cuentaservice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimientos")
@Valid
public class Movimientos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(length = 1)
    @Size(max = 1, message = "El tipo de movimiento no es válido")
    @NotNull(message = "El tipo de movimiento no puede ser un valor nulo")
    private String tipoMovimiento;

    @NotNull(message = "El valor no puede ser un valor nulo")
    private BigDecimal valor;

    private BigDecimal saldoInicial;

    private BigDecimal saldoDisponible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    @PrePersist
    protected void onCreate() {
        fecha = new Date();
    }
}
