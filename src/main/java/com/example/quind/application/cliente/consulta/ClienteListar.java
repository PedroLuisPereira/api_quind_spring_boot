package com.example.quind.application.cliente.consulta;


import com.example.quind.application.cliente.ClienteTransformador;
import com.example.quind.application.cliente.dto.ClienteRespuestaDto;
import com.example.quind.domain.service.ClienteService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ClienteListar {

    private final ClienteService clienteService;

    public ClienteListar(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Transactional(readOnly = true)
    public List<ClienteRespuestaDto> ejecutar() {

        return clienteService.listar()
                .stream()
                .map(ClienteTransformador::transformar)
                .toList();
    }


}
