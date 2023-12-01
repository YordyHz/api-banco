package com.devsu.cuentaservice.repository;

import com.devsu.cuentaservice.entity.Cuenta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByIdAndEstadoTrue(Long id);

    List<Cuenta> findByEstadoTrue();
}
