package com.example.quind.application.cuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaModificarEstadoDto {

    private String numeroDeCuenta;
    private String estado;

}
