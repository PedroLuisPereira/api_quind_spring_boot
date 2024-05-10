package com.example.quind.infrastructure.output.persistence;

import com.example.quind.domain.model.Cliente;
import com.example.quind.domain.ports.ClientePortRepository;
import com.example.quind.infrastructure.output.persistence.entity.ClienteEntity;
import com.example.quind.infrastructure.output.persistence.mapper.ClienteMapper;
import com.example.quind.infrastructure.output.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class ClientePersistenceAdapter implements ClientePortRepository {

    private final ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listar() {
        List<ClienteEntity> clienteEntities = clienteRepository.findAll();

        return clienteEntities.stream()
                .map(ClienteMapper::toCliente)
                .toList();
    }

    @Override
    public Optional<Cliente> listarByid(long id) {
        Optional<ClienteEntity> clienteEntity = clienteRepository.findById(id);
        return clienteEntity.map(ClienteMapper::toCliente);
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        ClienteEntity clienteEntity = ClienteMapper.toEntity(cliente);
        return ClienteMapper.toCliente(clienteRepository.save(clienteEntity));

    }

    @Override
    public void eliminar(long id) {
        clienteRepository.deleteById(id);
    }
}