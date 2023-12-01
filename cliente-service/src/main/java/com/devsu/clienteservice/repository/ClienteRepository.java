package com.devsu.clienteservice.repository;

import com.devsu.clienteservice.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
