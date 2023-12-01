package com.devsu.clienteservice.models;

import com.devsu.clienteservice.entity.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Entity
@Data
@Table(name = "cuenta")
public class Cuenta {
    @Id
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
