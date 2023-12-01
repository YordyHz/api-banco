package com.devsu.cuentaservice.service;

import com.devsu.cuentaservice.dto.CuentaDTO;
import com.devsu.cuentaservice.dto.request.CuentaRequest;
import com.devsu.cuentaservice.entity.Cuenta;
import com.devsu.cuentaservice.exception.CuentaException;
import com.devsu.cuentaservice.mapper.CuentaMapper;
import com.devsu.cuentaservice.models.Cliente;
import com.devsu.cuentaservice.repository.CuentaRepository;
import com.devsu.cuentaservice.util.CuentaConstant;
import com.devsu.cuentaservice.util.GeneralLogger;
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
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;
    
    @Autowired
    private CuentaMapper cuentaMapper;

    @Autowired
    private GeneralLogger generalLogger;

    public CuentaDTO crearCuenta(CuentaRequest cuentaRequest) {
        Long clienteId = cuentaRequest.getClienteId();

//        Cliente cliente = clienteRepository.findById(clienteId)
//                .orElseThrow(() -> new CuentaException(
//                HttpStatus.NOT_FOUND, //404 
//                String.format(ClienteConstant.CLIENTE_NO_ENCONTRADO_MESSAGE_ERROR,
//                        clienteId)
//        ));
        Cuenta cuenta = new Cuenta();
        cuenta.setInitialValues();
        cuenta.setNumeroCuenta(cuentaRequest.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaRequest.getTipoCuenta().name());
        cuenta.setSaldoInicial(cuentaRequest.getSaldoInicial());

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cuenta.setCliente(cliente);

        cuenta = cuentaRepository.save(cuenta);

        generalLogger.logInfo("Cuenta creada exitosamente.");
        return cuentaMapper.toDTO(cuenta);
    }

    public CuentaDTO actualizarCuenta(Long idCuenta, CuentaRequest cuentaRequest) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentaException(
                HttpStatus.NOT_FOUND, //404 
                String.format(CuentaConstant.CUENTA_NO_EXISTE_MESSAGE_ERROR,
                        cuentaRequest.getNumeroCuenta(),
                        cuentaRequest.getTipoCuenta().getDescripcion())
        ));

        Long clienteId = cuentaRequest.getClienteId();
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
//        Cliente cliente = clienteRepository.findByIdAndEstadoTrue(clienteId)
//                .orElseThrow(() -> new CuentaException(
//                HttpStatus.NOT_FOUND, //404 
//                String.format(ClienteConstant.CLIENTE_NO_ENCONTRADO_MESSAGE_ERROR,
//                        clienteId)
//        ));

        cuenta.setNumeroCuenta(cuentaRequest.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaRequest.getTipoCuenta().name());
        cuenta.setSaldoInicial(cuentaRequest.getSaldoInicial());
        cuenta.setLimiteDiario(cuentaRequest.getLimiteDiario());
        cuenta.setEstado(cuentaRequest.isEstado());
        cuenta.setCliente(cliente);

        cuenta = cuentaRepository.save(cuenta);

        generalLogger.logInfo("Cuenta actualizada exitosamente.");
        return cuentaMapper.toDTO(cuenta);
    }

    public CuentaDTO actualizarCuentaParcial(Long idCuenta, CuentaRequest cuentaRequest) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentaException(
                HttpStatus.NOT_FOUND, //404 
                String.format(CuentaConstant.CUENTA_NO_EXISTE_MESSAGE_ERROR,
                        cuentaRequest.getNumeroCuenta(),
                        cuentaRequest.getTipoCuenta().getDescripcion())
        ));

        Long clienteId = cuentaRequest.getClienteId();
        if (clienteId != null) {
//            Cliente cliente = clienteRepository.findByIdAndEstadoTrue(clienteId)
//                    .orElseThrow(() -> new CuentaException(
//                    HttpStatus.NOT_FOUND, //404 
//                    String.format(ClienteConstant.CLIENTE_NO_ENCONTRADO_MESSAGE_ERROR,
//                            clienteId)
//            ));
            Cliente cliente = new Cliente();
            cliente.setId(clienteId);
            cuenta.setCliente(cliente);
        }

        if (cuentaRequest.getNumeroCuenta() != null) {
            cuenta.setNumeroCuenta(cuentaRequest.getNumeroCuenta());
        }
        if (cuentaRequest.getTipoCuenta() != null) {
            cuenta.setTipoCuenta(cuentaRequest.getTipoCuenta().name());
        }
        if (cuentaRequest.getSaldoInicial() != null) {
            cuenta.setSaldoInicial(cuentaRequest.getSaldoInicial());
        }
        if (cuentaRequest.getLimiteDiario() != null) {
            cuenta.setLimiteDiario(cuentaRequest.getLimiteDiario());
        }

        cuenta = cuentaRepository.save(cuenta);

        generalLogger.logInfo("Cuenta actualizada exitosamente.");
        return cuentaMapper.toDTO(cuenta);
    }

    public void eliminarCuenta(Long cuentaId) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(cuentaId);
        if (cuentaOptional.isPresent()) {
            Cuenta cuenta = cuentaOptional.get();
            cuenta.setEstado(false);  //No se elimina fisicamente
            cuentaRepository.save(cuenta);
            generalLogger.logInfo("Cuenta eliminada exitosamente.");
        } else {
            throw new CuentaException(HttpStatus.NOT_FOUND,
                    String.format(CuentaConstant.CUENTA_NO_ELIMINADA_MESSAGE_ERROR,
                            cuentaId));
        }
    }

    public CuentaDTO obtenerCuentaPorId(Long cuentaId) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(cuentaId);
        if (cuentaOptional.isPresent()) {
            Cuenta cuenta = cuentaOptional.get();
            return cuentaMapper.toDTO(cuenta);
        } else {
            throw new CuentaException(HttpStatus.NOT_FOUND,
                    String.format(CuentaConstant.CUENTA_NO_ENCONTRADA_MESSAGE_ERROR,
                            cuentaId));
        }
    }

    public Iterable<CuentaDTO> obtenerTodasLasCuentas() {
        Iterable<Cuenta> cuentas = cuentaRepository.findAll();
        List<CuentaDTO> cuentaResponses = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            cuentaResponses.add(cuentaMapper.toDTO(cuenta));
        }

        return cuentaResponses;
    }

}
