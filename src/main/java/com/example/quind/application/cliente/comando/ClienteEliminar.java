package com.example.quind.application.cliente.comando;

import com.example.quind.domain.service.ClienteService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ClienteEliminar {


    private final ClienteService clienteService;

    public ClienteEliminar(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Transactional
    public void ejecutar(Long id) {
        clienteService.eliminar(id);
    }
}
