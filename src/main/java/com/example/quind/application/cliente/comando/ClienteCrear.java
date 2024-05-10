package com.example.quind.application.cliente.comando;

import com.example.quind.application.cliente.ClienteTransformador;
import com.example.quind.application.cliente.dto.ClienteDto;
import com.example.quind.application.cliente.dto.ClienteRespuestaDto;
import com.example.quind.domain.dto.ClienteSolicitud;
import com.example.quind.domain.model.Cliente;
import com.example.quind.domain.service.ClienteService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class ClienteCrear {

    private final ClienteService clienteService;

    public ClienteCrear(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Transactional
    public ClienteRespuestaDto ejecutar(ClienteDto clienteDto) {
        ClienteSolicitud clienteSolicitud = ClienteTransformador.transformar(clienteDto);
        Cliente cliente = clienteService.crear(clienteSolicitud);
        return ClienteTransformador.transformar(cliente);
    }
}
