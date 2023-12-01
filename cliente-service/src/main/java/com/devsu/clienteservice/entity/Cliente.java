package com.devsu.clienteservice.entity;

import com.devsu.clienteservice.models.Cuenta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
@Valid
public class Cliente extends Persona {

    private static final long serialVersionUID = 1L;

//    @Column(unique = true, nullable = false)
//    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "El clienteId debe contener solo caracteres alfanuméricos")
//    @NotNull(message = "El clienteId no puede ser un valor nulo")
//    private String clienteId;
    
    @Column(nullable = false)
    @NotNull(message = "La contraseña no puede ser un valor nulo")
    private String contraseña;

    //@Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean estado;
    
    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cuenta> cuentas = new ArrayList<>();

    public void setInitialValues() {
        this.estado = true;
    }
}
