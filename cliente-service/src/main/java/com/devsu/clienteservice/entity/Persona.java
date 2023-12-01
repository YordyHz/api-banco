package com.devsu.clienteservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Persona implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50, nullable = false)
    @NotNull(message = "El nombre no puede ser un valor nulo")
    @Size(min = 2, max = 50, message = "El nombre debe tener de 2 a 50 caracteres")
    private String nombre;

    @Column(length = 1, nullable = false)
    @NotNull(message = "El nombre no puede ser un valor nulo")
    @Size(max = 1, message = "El género no es válido")
    private String genero;

    private int edad;
    
    @Column(nullable = false)
    @NotNull(message = "La identificación no puede ser un valor nulo")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "La identificación debe contener solo caracteres alfanuméricos")
    private String identificacion;
    
    @Column(nullable = false)
    @NotNull(message = "La dirección no puede ser un valor nulo")
    private String direccion;
    
    @Column(nullable = false)
    @NotNull(message = "El teléfono no puede ser un valor nulo")
    private String telefono;
    
}
