package com.devsu.cuentaservice.repository;

import com.devsu.cuentaservice.entity.Cuenta;
import com.devsu.cuentaservice.entity.Movimientos;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {

    @Query("SELECT m FROM Movimientos m JOIN m.cuenta c JOIN c.cliente cl "
            + "WHERE c.id = :cuentaId "
            + "ORDER BY m.fecha DESC")
    List<Movimientos> findByCuenta(@Param("cuentaId") Long cuentaId);

    @Query("SELECT m FROM Movimientos m JOIN m.cuenta c JOIN c.cliente cl "
            + "WHERE cl.id = :clienteId "
            + "ORDER BY m.fecha DESC")
    List<Movimientos> findByCliente(@Param("clienteId") Long clienteId);

    @Query("SELECT m FROM Movimientos m JOIN m.cuenta c JOIN c.cliente cl "
            + "WHERE cl.id = :clienteId "
            + "AND DATE_TRUNC('day', m.fecha) "
            + " BETWEEN DATE_TRUNC('day', CAST(:fechaInicio AS timestamp)) "
            + " AND DATE_TRUNC('day', CAST(:fechaFin AS timestamp)) "
            + "ORDER BY m.fecha DESC")
    List<Movimientos> findByClienteAndFechaBetween(
            @Param("clienteId") Long clienteId,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin);

    @Query("SELECT ABS(COALESCE(SUM(m.valor), 0)) FROM Movimientos m "
            + "WHERE m.cuenta = :cuenta "
            + "AND m.fecha BETWEEN :startDateTime AND :endDateTime "
            + "AND m.tipoMovimiento  = :tipoMovimiento")
    Optional<BigDecimal> sumValorByCuentaAndFecha(
            @Param("cuenta") Cuenta cuenta,
            @Param("startDateTime") Date startDateTime,
            @Param("endDateTime") Date endDateTime,
            @Param("tipoMovimiento") String tipoMovimiento
    );
}
