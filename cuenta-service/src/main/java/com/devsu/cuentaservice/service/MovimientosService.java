package com.devsu.cuentaservice.service;

import com.devsu.cuentaservice.dto.MovimientosDTO;
import com.devsu.cuentaservice.dto.request.MovimientosRequest;
import com.devsu.cuentaservice.dto.response.MovimientosReporteResponse;
import com.devsu.cuentaservice.dto.response.MovimientosResponse;
import com.devsu.cuentaservice.entity.Cuenta;
import com.devsu.cuentaservice.entity.Movimientos;
import com.devsu.cuentaservice.enums.TipoCuenta;
import com.devsu.cuentaservice.enums.TipoMovimiento;
import com.devsu.cuentaservice.exception.MovimientosException;
import com.devsu.cuentaservice.mapper.MovimientosMapper;
import com.devsu.cuentaservice.repository.CuentaRepository;
import com.devsu.cuentaservice.repository.MovimientosRepository;
import com.devsu.cuentaservice.util.GeneralLogger;
import com.devsu.cuentaservice.util.MovimientosConstant;
import com.devsu.cuentaservice.util.Utilidades;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Service
public class MovimientosService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientosRepository movimientosRepository;

    @Autowired
    private MovimientosMapper movimientosMapper;

    @Autowired
    private GeneralLogger generalLogger;

    public MovimientosDTO registrarMovimiento(MovimientosRequest movimientosRequest) {
        Long cuentaId = movimientosRequest.getCuentaId();
        Cuenta cuenta = cuentaRepository.findByIdAndEstadoTrue(cuentaId)
                .orElseThrow(() -> new MovimientosException(
                HttpStatus.NOT_FOUND,
                String.format(MovimientosConstant.CUENTA_ASOCIADA_NO_ENCONTRADA_MESSAGE_ERROR)
        ));

        // Crear el movimiento
        Movimientos movimiento = crearMovimiento(cuenta, movimientosRequest.getValor());

        // Guardar el movimiento en la base de datos
        movimiento = movimientosRepository.save(movimiento);

        //Actualizar saldo en la cuenta
        cuenta.setSaldoInicial(movimiento.getSaldoDisponible());
        cuentaRepository.save(cuenta);

        // Devolver la respuesta mapeada
        generalLogger.logInfo("Movimiento creado exitosamente.");
        return movimientosMapper.toDTO(movimiento);
    }

    public MovimientosDTO actualizarMovimiento(Long id, MovimientosRequest movimientosRequest) {
        // Obtener el movimiento existente por su ID
        Movimientos movimientoExistente = obtenerMovimientoPorId(id);

        // Actualizar los campos del movimiento
        actualizarCamposMovimiento(movimientoExistente, movimientosRequest.getValor());

        // Guardar el movimiento actualizado en la base de datos
        Movimientos movimientoActualizado = movimientosRepository.save(movimientoExistente);

        //Actualizar saldo en la cuenta
        movimientoActualizado.getCuenta().setSaldoInicial(movimientoActualizado.getSaldoDisponible());
        cuentaRepository.save(movimientoActualizado.getCuenta());

        // Devolver la respuesta mapeada
        generalLogger.logInfo("Movimiento actualizado exitosamente.");
        return movimientosMapper.toDTO(movimientoActualizado);
    }

    public void eliminarMovimiento(Long id) {
        Optional<Movimientos> movimientosOptional = movimientosRepository.findById(id);
        if (movimientosOptional.isPresent()) {
            movimientosRepository.deleteById(id);
            generalLogger.logInfo("Movimiento eliminado exitosamente.");
        } else {
            throw new MovimientosException(HttpStatus.NOT_FOUND,
                    String.format(MovimientosConstant.MOVIMIENTO_NO_ELIMINADO_MESSAGE_ERROR,
                            id));
        }
    }

    public Iterable<MovimientosResponse> obtenerMovimientosPorCuenta(Long idCuenta) {
        Iterable<Movimientos> movimientoss = movimientosRepository.findByCuenta(idCuenta);
        List<MovimientosResponse> movimientosResponses = new ArrayList<>();

        for (Movimientos movimientos : movimientoss) {
            movimientosResponses.add(movimientosMapper.toResponse(movimientos));
        }

        return movimientosResponses;
    }

    public Iterable<MovimientosResponse> obtenerMovimientosPorCliente(Long idCliente) {
        Iterable<Movimientos> movimientos = movimientosRepository.findByCliente(idCliente);
        List<MovimientosResponse> movimientosResponses = new ArrayList<>();

        for (Movimientos movimiento : movimientos) {
            movimientosResponses.add(movimientosMapper.toResponse(movimiento));
        }

        return movimientosResponses;
    }

    public List<MovimientosReporteResponse> obtenerMovimientosPorClienteYFecha(Long idCliente, LocalDate fechaInicio, LocalDate fechaFin) {
        Date fechaInicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFinDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Iterable<Movimientos> movimientos = movimientosRepository.findByClienteAndFechaBetween(idCliente, fechaInicioDate, fechaFinDate);

        Map<String, MovimientosReporteResponse> reportePorCliente = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (Movimientos movimiento : movimientos) {
            String clienteNombre = movimiento.getCuenta().getCliente().getNombre();

            MovimientosReporteResponse reporteCliente = reportePorCliente.computeIfAbsent(clienteNombre, k -> new MovimientosReporteResponse());
            reporteCliente.setCliente(clienteNombre);

            List<MovimientosReporteResponse.Cuenta> cuentas = reporteCliente.getCuentas();
            if (cuentas == null) {
                cuentas = new ArrayList<>();
                reporteCliente.setCuentas(cuentas);
            }

            MovimientosReporteResponse.Cuenta cuenta = reporteCliente
                    .getCuentas()
                    .stream()
                    .filter(c -> c.getNumeroCuenta().equals(movimiento.getCuenta().getNumeroCuenta().toString()))
                    .findFirst()
                    .orElseGet(() -> {
                        MovimientosReporteResponse.Cuenta nuevaCuenta = reporteCliente.new Cuenta();
                        nuevaCuenta.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta().toString());
                        nuevaCuenta.setTipoCuenta(TipoCuenta.getDescripcion(movimiento.getCuenta().getTipoCuenta()));
                        nuevaCuenta.setEstado(movimiento.getCuenta().isEstado());

                        nuevaCuenta.setMovimientos(new ArrayList<>());

                        reporteCliente.getCuentas().add(nuevaCuenta);
                        return nuevaCuenta;
                    });

            MovimientosReporteResponse.Movimiento movimientoResponse = reporteCliente.new Movimiento();
            movimientoResponse.setFecha(dateFormat.format(movimiento.getFecha()));
            movimientoResponse.setSaldoInicial(movimiento.getSaldoInicial());
            movimientoResponse.setMovimiento(movimiento.getValor());
            movimientoResponse.setSaldoDisponible(movimiento.getSaldoDisponible());

            cuenta.getMovimientos().add(movimientoResponse);
        }

        return new ArrayList<>(reportePorCliente.values());
    }

    private void validarLimiteDiario(Cuenta cuenta, BigDecimal valor, String tipoMovimiento) {
        if (tipoMovimiento.equals(TipoMovimiento.C.name())) {
            return;
        }
        BigDecimal limiteDiario = cuenta.getLimiteDiario();

        LocalDate today = LocalDate.now();
        Date startOfDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(today.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

        BigDecimal totalRetirosDiarios = movimientosRepository
                .sumValorByCuentaAndFecha(cuenta, startOfDay, endOfDay, TipoMovimiento.D.name())
                .orElse(BigDecimal.ZERO);

        if (limiteDiario.compareTo(totalRetirosDiarios.add(valor.abs())) < 0) {
            throw new MovimientosException(HttpStatus.BAD_REQUEST,
                    MovimientosConstant.LIMITE_DIARIO_ALCANZADO_MESSAGE_ERROR);
        }
    }

    private void actualizarSaldosMovimiento(Movimientos movimiento) {
        BigDecimal saldoInicial = movimiento.getCuenta().getSaldoInicial();
        BigDecimal saldoDisponible = saldoInicial.add(movimiento.getValor());

        // Validar si el saldo disponible es cero y el movimiento es de tipo débito
        if (Utilidades.isZeroOrLess(saldoDisponible) && movimiento.getTipoMovimiento().equals(TipoMovimiento.D.name())) {
            throw new MovimientosException(HttpStatus.BAD_REQUEST,
                    MovimientosConstant.SALDO_NO_DISPONIBLE_MESSAGE_ERROR);
        }

        movimiento.setSaldoInicial(saldoInicial);
        movimiento.setSaldoDisponible(saldoDisponible);
    }

    private Movimientos crearMovimiento(Cuenta cuenta, BigDecimal valor) {
        String tipoMovimiento = Utilidades.tipoMovimiento(valor).name();
        // Validar el límite diario de retiro
        validarLimiteDiario(cuenta, valor, tipoMovimiento);

        Movimientos movimiento = new Movimientos();
        movimiento.setCuenta(cuenta);
        movimiento.setValor(valor);

        // Configurar tipo de movimiento y actualizar saldos
        movimiento.setTipoMovimiento(tipoMovimiento);
        actualizarSaldosMovimiento(movimiento);

        return movimiento;
    }

    private Movimientos obtenerMovimientoPorId(Long id) {
        // Obtener el movimiento existente por su ID
        Movimientos movimientoExistente = movimientosRepository.findById(id)
                .orElseThrow(() -> new MovimientosException(HttpStatus.NOT_FOUND,
                String.format(MovimientosConstant.MOVIMIENTO_NO_ENCONTRADO_MESSAGE_ERROR, id)
        ));
        return movimientoExistente;
    }

    private Cuenta validarMovimientoCuenta(Movimientos movimiento) {
        // Validar si el movimiento pertenece a la cuenta y obtener la cuenta asociada
        Cuenta cuenta = movimiento.getCuenta();
        if (cuenta == null || !cuenta.isEstado()) {
            throw new MovimientosException(HttpStatus.NOT_FOUND,
                    MovimientosConstant.CUENTA_ASOCIADA_NO_ENCONTRADA_MESSAGE_ERROR
            );
        }
        return cuenta;
    }

    private void actualizarCamposMovimiento(Movimientos movimiento, BigDecimal valor) {
        Cuenta cuenta = validarMovimientoCuenta(movimiento);

        String tipoMovimiento = Utilidades.tipoMovimiento(valor).name();

        validarLimiteDiario(cuenta, valor, tipoMovimiento);

        movimiento.setValor(valor);
        movimiento.setTipoMovimiento(tipoMovimiento);

        actualizarSaldosMovimiento(movimiento);
    }

}
