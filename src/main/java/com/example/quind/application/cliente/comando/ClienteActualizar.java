package com.example.quind.application.cliente.comando;

import com.example.quind.application.cliente.ClienteTransformador;
import com.example.quind.application.cliente.dto.ClienteDto;
import com.example.quind.application.cliente.dto.ClienteRespuestaDto;
import com.example.quind.domain.dto.ClienteSolicitud;
import com.example.quind.domain.model.Cliente;
import com.example.quind.domain.service.ClienteService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

@Component
public class ClienteActualizar {

    private final ClienteService clienteService;

    public ClienteActualizar(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Transactional
    public ClienteRespuestaDto ejecutar(Long id, ClienteDto clienteDto) {
        ClienteSolicitud clienteSolicitud = ClienteTransformador.transformar(clienteDto);
        Cliente cliente = clienteService.actualizar(id,clienteSolicitud);
        return  ClienteTransformador.transformar(cliente);
    }
}
