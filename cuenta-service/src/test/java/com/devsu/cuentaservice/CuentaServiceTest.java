package com.devsu.cuentaservice;

import com.devsu.cuentaservice.dto.CuentaDTO;
import com.devsu.cuentaservice.entity.Cuenta;
import com.devsu.cuentaservice.exception.CuentaException;
import com.devsu.cuentaservice.mapper.CuentaMapper;
import com.devsu.cuentaservice.repository.CuentaRepository;
import com.devsu.cuentaservice.service.CuentaService;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public class CuentaServiceTest {
    
    @Mock
    private CuentaRepository cuentaRepository;
    
    @Mock
    private CuentaMapper cuentaMapper;

    @InjectMocks
    private CuentaService cuentaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerCuentaPorIdExistente() {

        Long cuentaId = 1L;
        Cuenta cuentaMock = new Cuenta();
        cuentaMock.setId(cuentaId);
        when(cuentaRepository.findById(cuentaId)).thenReturn(Optional.of(cuentaMock));

        CuentaDTO resultado = cuentaService.obtenerCuentaPorId(cuentaId);

        assertEquals(cuentaMapper.toDTO(cuentaMock), resultado);
    }

    @Test
    public void testObtenerCuentaPorIdNoExistente() {

        Long cuentaId = 100L;
        when(cuentaRepository.findById(cuentaId)).thenReturn(Optional.empty());

        CuentaException excepcion = org.junit.jupiter.api.Assertions.assertThrows(
                CuentaException.class,
                () -> cuentaService.obtenerCuentaPorId(cuentaId)
        );

        assertEquals(HttpStatus.NOT_FOUND, excepcion.getStatusCode());
    }
    
}
