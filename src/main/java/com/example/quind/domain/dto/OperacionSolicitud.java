package com.example.quind.domain.dto;


public class OperacionSolicitud {

    private final String numeroCuentaOrigen;
    private final String numeroCuentaDestino;
    private final double valor;

    public OperacionSolicitud(String numeroCuentaOrigen, String numeroCuentaDestino, double valor) {
        this.numeroCuentaOrigen = numeroCuentaOrigen;
        this.numeroCuentaDestino = numeroCuentaDestino;
        this.valor = valor;
    }

    public String getNumeroCuentaOrigen() {
        return numeroCuentaOrigen;
    }

    public String getNumeroCuentaDestino() {
        return numeroCuentaDestino;
    }

    public double getValor() {
        return valor;
    }

}
