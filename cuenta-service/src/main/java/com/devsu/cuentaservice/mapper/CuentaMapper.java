package com.devsu.cuentaservice.mapper;

import com.devsu.cuentaservice.dto.CuentaDTO;
import com.devsu.cuentaservice.dto.request.CuentaRequest;
import com.devsu.cuentaservice.entity.Cuenta;
import com.devsu.cuentaservice.enums.TipoCuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface CuentaMapper {

    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    //toEntity
//    Cuenta toEntity(CuentaDTO cuentaDTO);
//
//    Cuenta toEntity(CuentaRequest request);

    //fromEntity
    @Mapping(source = "cuenta.cliente.id", target = "clienteId")
    CuentaDTO toDTO(Cuenta cuenta);
    
    default String getDescripcionTipoCuenta(Cuenta cuenta){
        return TipoCuenta.getDescripcion(cuenta.getTipoCuenta());
    }
    
    @Mapping(source = "cuenta.cliente.id", target = "clienteId")
    CuentaRequest toRequest(Cuenta cuenta);
}
