package com.example.quind.application.cuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDto {

    private String tipoDeCuenta;
    private String saldo;
    private String exentaGMF;
    private String clienteId;

}

