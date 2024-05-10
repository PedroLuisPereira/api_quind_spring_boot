package com.example.quind.domain.ports;

import com.example.quind.domain.model.Cuenta;

import java.util.List;


public interface CuentaPortRepository {

    List<Cuenta> listar();

    List<Cuenta> listarByNumeroCuenta(String numeroCuenta);

    List<Cuenta> listarByClienteId(long clienteID);

    Cuenta guardar(Cuenta cuenta);

}

