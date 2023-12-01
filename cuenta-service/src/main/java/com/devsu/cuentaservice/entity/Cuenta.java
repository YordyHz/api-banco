package com.devsu.cuentaservice.entity;

import com.devsu.cuentaservice.models.Cliente;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "cuenta")
@Valid
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El número de cuenta no puede ser un valor nulo")
    private Long numeroCuenta;

    @Column(length = 1)
    @Size(max = 1, message = "El tipo de cuenta no es válido")
    @NotNull(message = "El tipo de cuenta no puede ser un valor nulo")
    private String tipoCuenta;

    @NotNull(message = "El saldo inicial no puede ser un valor nulo")
    private BigDecimal saldoInicial;

    @NotNull(message = "El límite diario no puede ser un valor nulo")
    private BigDecimal limiteDiario;

    //@Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimientos> movimientos = new ArrayList<>();

    public void setInitialValues() {
        this.estado = true;
        this.limiteDiario = BigDecimal.valueOf(1000);
    }
}
