package com.example.quind.application.cliente.consulta;

import com.example.quind.application.cliente.ClienteTransformador;
import com.example.quind.application.cliente.dto.ClienteRespuestaDto;
import com.example.quind.domain.service.ClienteService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ClienteListarPorId {

    private final ClienteService clienteService;

    public ClienteListarPorId(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Transactional(readOnly = true)
    public ClienteRespuestaDto ejecutar(Long id) {
        return ClienteTransformador.transformar(clienteService.listarByid(id));
    }
}
