package com.example.quind.domain.dto;


public class CuentaSolicitud {

    private String tipoDeCuenta;
    private String saldo;
    private String exentaGMF;
    private String clienteId;

    public CuentaSolicitud(String tipoDeCuenta, String saldo, String exentaGMF, String clienteId) {
        this.tipoDeCuenta = tipoDeCuenta;
        this.saldo = saldo;
        this.exentaGMF = exentaGMF;
        this.clienteId = clienteId;
    }

    public String getTipoDeCuenta() {
        return tipoDeCuenta;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getExentaGMF() {
        return exentaGMF;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setTipoDeCuenta(String tipoDeCuenta) {
        this.tipoDeCuenta = tipoDeCuenta;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public void setExentaGMF(String exentaGMF) {
        this.exentaGMF = exentaGMF;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

}
