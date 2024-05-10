package com.example.quind.infrastructure.output.persistence;


import com.example.quind.domain.model.Cuenta;
import com.example.quind.domain.ports.CuentaPortRepository;
import com.example.quind.infrastructure.output.persistence.mapper.CuentaMapper;
import com.example.quind.infrastructure.output.persistence.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class CuentaPersistenceAdapter implements CuentaPortRepository {

    private final CuentaRepository cuentaRepository;

    @Override
    public List<Cuenta> listar() {
        return cuentaRepository.findAll().stream()
                .map(CuentaMapper::toCuenta)
                .toList();
    }

    @Override
    public List<Cuenta> listarByNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroDeCuenta(numeroCuenta)
                .stream()
                .map(CuentaMapper::toCuenta)
                .toList();
    }

    @Override
    public List<Cuenta> listarByClienteId(long clienteId) {
        return cuentaRepository.findByClienteId(clienteId)
                .stream()
                .map(CuentaMapper::toCuenta)
                .toList();
    }

    @Override
    public Cuenta guardar(Cuenta cuenta) {
        return CuentaMapper.toCuenta(cuentaRepository.save(CuentaMapper.toEntity(cuenta)));
    }

}