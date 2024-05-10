package com.example.quind.infrastructure.output.persistence.mapper;

import com.example.quind.domain.model.Cuenta;
import com.example.quind.infrastructure.output.persistence.entity.CuentaEntity;


public class CuentaMapper {

    private CuentaMapper() {
    }

    public static Cuenta toCuenta(CuentaEntity entity) {
        return Cuenta.getInstance(
                entity.getId(),
                entity.getTipoDeCuenta(),
                entity.getNumeroDeCuenta(),
                entity.getEstado(),
                entity.getSaldo(),
                entity.getExentaGMF(),
                entity.getFechaDeCreacion(),
                entity.getFechaDeModificacion(),
                ClienteMapper.toCliente(entity.getClienteEntity())
        );
    }

    public static CuentaEntity toEntity(Cuenta cuenta) {
        return new CuentaEntity(
                cuenta.getId(),
                cuenta.getTipoDeCuenta(),
                cuenta.getNumeroDeCuenta(),
                cuenta.getEstado(),
                cuenta.getSaldo(),
                cuenta.getExentaGMF(),
                cuenta.getFechaDeCreacion(),
                cuenta.getFechaDeModificacion(),
                ClienteMapper.toEntity(cuenta.getCliente())
        );
    }

}