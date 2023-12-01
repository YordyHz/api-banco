package com.devsu.clienteservice;

import com.devsu.clienteservice.entity.Cliente;
import com.devsu.clienteservice.exception.ClienteException;
import com.devsu.clienteservice.repository.ClienteRepository;
import com.devsu.clienteservice.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerClientePorIdExistente() {

        Long clienteId = 1L;
        Cliente clienteMock = new Cliente();
        clienteMock.setId(clienteId);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));

        Cliente resultado = clienteService.obtenerClientePorId(clienteId);

        assertEquals(clienteMock, resultado);
    }

    @Test
    public void testObtenerClientePorIdNoExistente() {

        Long clienteId = 100L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        ClienteException excepcion = org.junit.jupiter.api.Assertions.assertThrows(
                ClienteException.class,
                () -> clienteService.obtenerClientePorId(clienteId)
        );

        assertEquals(HttpStatus.NOT_FOUND, excepcion.getStatusCode());
    }

}
