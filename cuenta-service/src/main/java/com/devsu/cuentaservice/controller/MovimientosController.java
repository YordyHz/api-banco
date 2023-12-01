package com.devsu.cuentaservice.controller;

import com.devsu.cuentaservice.util.GeneralLogger;
import com.devsu.cuentaservice.dto.MovimientosDTO;
import com.devsu.cuentaservice.dto.request.MovimientosRequest;
import com.devsu.cuentaservice.dto.response.MovimientosReporteResponse;
import com.devsu.cuentaservice.dto.response.MovimientosResponse;
import com.devsu.cuentaservice.entity.Movimientos;
import com.devsu.cuentaservice.service.MovimientosService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin //(origins = "http://localhost:4200")
public class MovimientosController {

    private final MovimientosService movimientosService;
    private final GeneralLogger movimientosLogger;

    @Autowired
    public MovimientosController(MovimientosService movimientosService, GeneralLogger movimientosLogger) {
        this.movimientosService = movimientosService;
        this.movimientosLogger = movimientosLogger;
    }

    @GetMapping("/cuenta/{id}")
    public Iterable<MovimientosResponse> obtenerMovimientosPorCuenta(@PathVariable Long id) {
        movimientosLogger.logInfo("Consultando movimientos con ID de cuenta: " + id);
        return movimientosService.obtenerMovimientosPorCuenta(id);
    }

    @GetMapping("/cliente/{id}")
    public Iterable<MovimientosResponse> obtenerMovimientosPorCliente(@PathVariable Long id) {
        movimientosLogger.logInfo("Consultando movimientos con ID de cliente: " + id);
        return movimientosService.obtenerMovimientosPorCliente(id);
    }

    @GetMapping("/reportes")
    public Iterable<MovimientosReporteResponse> obtenerMovimientosPorClienteYFecha(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fechaFin) {
        movimientosLogger.logInfo("Consultando movimientos con ID de cliente: " + clienteId);
        return movimientosService.obtenerMovimientosPorClienteYFecha(clienteId, fechaInicio, fechaFin);
    }

    @PostMapping
    public MovimientosDTO registrarMovimiento(@RequestBody MovimientosRequest request) {
        movimientosLogger.logInfo("Creando nuevo movimiento");
        return movimientosService.registrarMovimiento(request);
    }

    @PutMapping("/{id}")
    public MovimientosDTO actualizarMovimiento(@PathVariable Long id, @RequestBody MovimientosRequest request) {
        movimientosLogger.logInfo("Actualizando movimiento con ID: " + id);
        return movimientosService.actualizarMovimiento(id, request);
    }

    @PatchMapping("/{id}")
    public MovimientosDTO actualizarMovimientoParcial(@PathVariable Long id, @RequestBody MovimientosRequest request) {
        movimientosLogger.logInfo("Actualizando parcialmente movimiento con ID: " + id);
        return movimientosService.actualizarMovimiento(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminarMovimiento(@PathVariable Long id) {
        movimientosLogger.logInfo("Eliminando movimiento con ID: " + id);
        movimientosService.eliminarMovimiento(id);
    }
}
