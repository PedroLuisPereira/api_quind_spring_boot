package com.example.quind.domain.ports;

import com.example.quind.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClientePortRepository {

    List<Cliente> listar();

    Optional<Cliente> listarByid(long id);

    Cliente guardar(Cliente cliente);

    void eliminar(long id);

}

