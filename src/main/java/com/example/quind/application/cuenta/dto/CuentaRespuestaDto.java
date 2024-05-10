package com.example.quind.application.cuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaRespuestaDto {

    private Long id;
    private String tipoDeCuenta;
    private String numeroDeCuenta;
    private String estado;
    private double saldo;
    private String exentaGMF;
    private String fechaDeCreacion;
    private String fechaDeModificacion;

}
