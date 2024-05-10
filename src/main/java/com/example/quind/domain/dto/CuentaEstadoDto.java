package com.example.quind.domain.dto;


public class CuentaEstadoDto {

    private final String numeroDeCuenta;
    private final String estado;


    public CuentaEstadoDto(String numeroDeCuenta, String estado) {
        this.numeroDeCuenta = numeroDeCuenta;
        this.estado = estado;
    }

    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public String getEstado() {
        return estado;
    }

}
