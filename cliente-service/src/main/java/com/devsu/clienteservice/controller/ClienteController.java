package com.devsu.clienteservice.controller;

import com.devsu.clienteservice.entity.Cliente;
import com.devsu.clienteservice.service.ClienteService;
import com.devsu.clienteservice.util.GeneralLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@RestController
@RequestMapping("/api/clientes")
@CrossOrigin//(origins = "http://localhost:4200")
public class ClienteController {
    private final ClienteService clienteService;
    private final GeneralLogger clienteLogger;

    @Autowired
    public ClienteController(ClienteService clienteService, GeneralLogger clienteLogger) {
        this.clienteService = clienteService;
        this.clienteLogger = clienteLogger;
    }
    
    @GetMapping
    public Iterable<Cliente> obtenerTodosLosClientes() {
        clienteLogger.logInfo("Consultando listado de todos los clientes");
        return clienteService.obtenerTodosLosClientes();
    }

    @GetMapping("/{id}")
    public Cliente obtenerClientePorId(@PathVariable Long id) {
        clienteLogger.logInfo("Consultando cliente con ID: " + id);
        return clienteService.obtenerClientePorId(id);
    }

    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente request) {
        clienteLogger.logInfo("Creando nuevo cliente");
        return clienteService.crearCliente(request);
    }

    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente request) {
        clienteLogger.logInfo("Actualizando cliente con ID: " + id);
        return clienteService.actualizarCliente(id, request);
    }

    @PatchMapping("/{id}")
    public Cliente actualizarClienteParcial(@PathVariable Long id, @RequestBody Cliente request) {
        clienteLogger.logInfo("Actualizando parcialmente cliente con ID: " + id);
        return clienteService.actualizarClienteParcial(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteLogger.logInfo("Eliminando cliente con ID: " + id);
        clienteService.eliminarCliente(id);
    }
}
