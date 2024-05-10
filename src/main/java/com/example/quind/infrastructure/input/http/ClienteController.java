package com.example.quind.infrastructure.input.http;


import com.example.quind.application.cliente.comando.ClienteActualizar;
import com.example.quind.application.cliente.comando.ClienteCrear;
import com.example.quind.application.cliente.comando.ClienteEliminar;
import com.example.quind.application.cliente.consulta.ClienteListar;
import com.example.quind.application.cliente.consulta.ClienteListarPorId;
import com.example.quind.application.cliente.dto.ClienteDto;
import com.example.quind.application.cliente.dto.ClienteRespuestaDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteCrear clienteCrear;
    private final ClienteActualizar clienteActualizar;
    private final ClienteEliminar clienteEliminar;
    private final ClienteListar clienteListar;
    private final ClienteListarPorId clienteListarPorId;


    public ClienteController(
            ClienteCrear clienteCrear,
            ClienteActualizar clienteActualizar,
            ClienteEliminar clienteEliminar,
            ClienteListar clienteListar,
            ClienteListarPorId clienteListarPorId
    ) {
        this.clienteCrear = clienteCrear;
        this.clienteActualizar = clienteActualizar;
        this.clienteEliminar = clienteEliminar;
        this.clienteListar = clienteListar;
        this.clienteListarPorId = clienteListarPorId;
    }

    @GetMapping("")
    public List<ClienteRespuestaDto> list() {
        return clienteListar.ejecutar();
    }

    @GetMapping("/{id}")
    public ClienteRespuestaDto listByid(@PathVariable(value = "id") Long id) {
        return clienteListarPorId.ejecutar(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteRespuestaDto create(@RequestBody ClienteDto clienteDto) {
        return clienteCrear.ejecutar(clienteDto);
    }

    @PutMapping("/{id}")
    public ClienteRespuestaDto update(@PathVariable(value = "id") Long id, @RequestBody ClienteDto clienteDto) {
        return clienteActualizar.ejecutar(id, clienteDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        clienteEliminar.ejecutar(id);
    }

}
