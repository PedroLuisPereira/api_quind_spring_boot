package com.example.quind.infrastructure.output.persistence.mapper;

import com.example.quind.domain.model.Cliente;
import com.example.quind.infrastructure.output.persistence.entity.ClienteEntity;

public class ClienteMapper {

    private ClienteMapper() {
    }

    public static Cliente toCliente(ClienteEntity entity) {
        return Cliente.getInstance(
                entity.getId(),
                entity.getTipoDeIdentificacion(),
                entity.getNumeroDeIdentificacion(),
                entity.getNombres(),
                entity.getApellidos(),
                entity.getCorreoElectronico(),
                entity.getFechaDeNacimiento(),
                entity.getFechaDeCreacion(),
                entity.getFechaDeModificacion()
        );
    }

    public static ClienteEntity toEntity(Cliente cliente) {
        return new ClienteEntity(
                cliente.getId(),
                cliente.getTipoDeIdentificacion(),
                cliente.getNumeroDeIdentificacion(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getCorreoElectronico(),
                cliente.getFechaDeNacimiento(),
                cliente.getFechaDeCreacion(),
                cliente.getFechaDeModificacion()
        );
    }

}