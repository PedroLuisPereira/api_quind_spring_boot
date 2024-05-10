package com.example.quind.application.cuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OperacionDto {

    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;
    private double valor;

}

