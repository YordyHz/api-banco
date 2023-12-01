package com.devsu.cuentaservice.controller;

import com.devsu.cuentaservice.dto.CuentaDTO;
import com.devsu.cuentaservice.dto.request.CuentaRequest;
import com.devsu.cuentaservice.util.GeneralLogger;
import com.devsu.cuentaservice.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@RestController
@RequestMapping("/api/cuentas")
@CrossOrigin //(origins = "http://localhost:4200")
public class CuentaController {

    private final CuentaService cuentaService;
    private final GeneralLogger cuentaLogger;

    @Autowired
    public CuentaController(CuentaService cuentaService, GeneralLogger cuentaLogger) {
        this.cuentaService = cuentaService;
        this.cuentaLogger = cuentaLogger;
    }

    @GetMapping
    public Iterable<CuentaDTO> obtenerTodasLasCuentas() {
        cuentaLogger.logInfo("Consultando listado de todas las cuentas");
        return cuentaService.obtenerTodasLasCuentas();
    }

    @GetMapping("/{id}")
    public CuentaDTO obtenerCuentaPorId(@PathVariable Long id) {
        cuentaLogger.logInfo("Consultando cuenta con ID: " + id);
        return cuentaService.obtenerCuentaPorId(id);
    }

    @PostMapping
    public CuentaDTO crearCuenta(@RequestBody CuentaRequest request) {
        cuentaLogger.logInfo("Creando nueva cuenta");
        return cuentaService.crearCuenta(request);
    }

    @PutMapping("/{id}")
    public CuentaDTO actualizarCuenta(@PathVariable Long id, @RequestBody CuentaRequest request) {
        cuentaLogger.logInfo("Actualizando cuenta con ID: " + id);
        return cuentaService.actualizarCuenta(id, request);
    }

    @PatchMapping("/{id}")
    public CuentaDTO actualizarCuentaParcial(@PathVariable Long id, @RequestBody CuentaRequest request) {
        cuentaLogger.logInfo("Actualizando parcialmente cuenta con ID: " + id);
        return cuentaService.actualizarCuentaParcial(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminarCuenta(@PathVariable Long id) {
        cuentaLogger.logInfo("Eliminando cuenta con ID: " + id);
        cuentaService.eliminarCuenta(id);
    }
}
