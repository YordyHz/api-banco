package com.devsu.clienteservice.service;

import com.devsu.clienteservice.entity.Cliente;
import com.devsu.clienteservice.exception.ClienteException;
import com.devsu.clienteservice.repository.ClienteRepository;
import com.devsu.clienteservice.util.ClienteConstant;
import com.devsu.clienteservice.util.GeneralLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private GeneralLogger clienteLogger;

    public Cliente crearCliente(Cliente cliente) {
        cliente.setInitialValues();
        cliente = clienteRepository.save(cliente);
        clienteLogger.logInfo("Cliente creado exitosamente.");
        return cliente;
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException(
                HttpStatus.NOT_FOUND,
                String.format(ClienteConstant.CLIENTE_NO_EXISTE_MESSAGE_ERROR,
                        cliente.getIdentificacion(),
                        cliente.getNombre())
        ));
        clienteExistente = cliente;
        clienteExistente.setId(id); // Asignar el ID al cliente actualizado
        clienteExistente = clienteRepository.save(clienteExistente);
        clienteLogger.logInfo("Cliente actualizado exitosamente.");
        return clienteExistente;
    }

    public Cliente actualizarClienteParcial(Long id, Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException(
                HttpStatus.NOT_FOUND,
                String.format(ClienteConstant.CLIENTE_NO_ENCONTRADO_MESSAGE_ERROR,
                        id)
        ));

        if (cliente.getNombre() != null) {
            clienteExistente.setNombre(cliente.getNombre());
        }
        if (cliente.getGenero() != null) {
            clienteExistente.setGenero(cliente.getGenero());
        }
        if (cliente.getEdad() != 0) {
            clienteExistente.setEdad(cliente.getEdad());
        }
        if (cliente.getIdentificacion() != null) {
            clienteExistente.setIdentificacion(cliente.getIdentificacion());
        }
        if (cliente.getDireccion() != null) {
            clienteExistente.setDireccion(cliente.getDireccion());
        }
        if (cliente.getTelefono() != null) {
            clienteExistente.setTelefono(cliente.getTelefono());
        }
        if (cliente.getContraseña() != null) {
            clienteExistente.setContraseña(cliente.getContraseña());
        }

        clienteExistente = clienteRepository.save(clienteExistente);
        clienteLogger.logInfo("Cliente actualizado exitosamente.");
        return clienteExistente;
    }

    public void eliminarCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setEstado(false);  //No se elimina fisicamente
            clienteRepository.save(cliente);
            clienteLogger.logInfo("Cliente eliminado exitosamente.");
        } else {
            throw new ClienteException(HttpStatus.NOT_FOUND,
                    String.format(ClienteConstant.CLIENTE_NO_ELIMINADO_MESSAGE_ERROR,
                            id));
        }
    }

    public Cliente obtenerClientePorId(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            return cliente;
        } else {
            throw new ClienteException(HttpStatus.NOT_FOUND,
                    String.format(ClienteConstant.CLIENTE_NO_ENCONTRADO_MESSAGE_ERROR,
                            id));
        }
    }

    public Iterable<Cliente> obtenerTodosLosClientes() {
        Iterable<Cliente> clientes = clienteRepository.findAll();
        List<Cliente> clienteResponses = new ArrayList<>();

        for (Cliente cliente : clientes) {
            clienteResponses.add(cliente);
        }
        return clienteResponses;
    }

}
