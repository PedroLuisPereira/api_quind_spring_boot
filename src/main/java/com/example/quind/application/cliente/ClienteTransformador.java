package com.example.quind.application.cliente;

import com.example.quind.application.cliente.dto.ClienteDto;
import com.example.quind.application.cliente.dto.ClienteRespuestaDto;
import com.example.quind.domain.dto.ClienteSolicitud;
import com.example.quind.domain.model.Cliente;


public class ClienteTransformador {

    private ClienteTransformador() {
    }

    public static ClienteSolicitud transformar(ClienteDto clienteDto) {
        return new ClienteSolicitud(
                clienteDto.getId(),
                clienteDto.getTipoDeIdentificacion(),
                clienteDto.getNumeroDeIdentificacion(),
                clienteDto.getNombres(),
                clienteDto.getApellidos(),
                clienteDto.getCorreoElectronico(),
                clienteDto.getFechaDeNacimiento()
        );
    }

    public static ClienteDto transformar (ClienteSolicitud clienteSolicitud) {
        return new ClienteDto(
                clienteSolicitud.getId(),
                clienteSolicitud.getTipoDeIdentificacion(),
                clienteSolicitud.getNumeroDeIdentificacion(),
                clienteSolicitud.getNombres(),
                clienteSolicitud.getApellidos(),
                clienteSolicitud.getCorreoElectronico(),
                clienteSolicitud.getFechaDeNacimiento()
        );
    }

    public static ClienteRespuestaDto transformar(Cliente cliente) {
        return new ClienteRespuestaDto(
                cliente.getId(),
                cliente.getTipoDeIdentificacion(),
                cliente.getNumeroDeIdentificacion(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getCorreoElectronico(),
                cliente.getFechaDeNacimiento().toString(),
                cliente.getFechaDeCreacion().toString(),
                cliente.getFechaDeModificacion().toString()
        );
    }
}
