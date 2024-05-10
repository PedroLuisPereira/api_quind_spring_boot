package com.example.quind.domain.model;


import java.io.Serializable;
import java.util.Date;

public class Cuenta implements Serializable {

    private final Long id;
    private final String tipoDeCuenta;
    private final String numeroDeCuenta;
    private final String estado;
    private final double saldo;
    private final String exentaGMF;
    private final Date fechaDeCreacion;
    private final Date fechaDeModificacion;
    private final Cliente cliente;

    private Cuenta(Long id, String tipoDeCuenta, String numeroDeCuenta, String estado, double saldo, String exentaGMF, Date fechaDeCreacion, Date fechaDeModificacion, Cliente cliente) {
        this.id = id;
        this.tipoDeCuenta = tipoDeCuenta;
        this.numeroDeCuenta = numeroDeCuenta;
        this.estado = estado;
        this.saldo = saldo;
        this.exentaGMF = exentaGMF;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
        this.cliente = cliente;
    }

    public static Cuenta getInstance(
            Long id,
            String tipoDeCuenta,
            String numeroDeCuenta,
            String estado,
            double saldo,
            String exentaGMF,
            Date fechaDeCreacion,
            Date fechaDeModificacion,
            Cliente cliente) {

        return new Cuenta(
                id,
                tipoDeCuenta,
                numeroDeCuenta,
                estado,
                saldo,
                exentaGMF,
                fechaDeCreacion,
                fechaDeModificacion,
                cliente
        );
    }

    public Long getId() {
        return id;
    }

    public String getTipoDeCuenta() {
        return tipoDeCuenta;
    }

    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public String getEstado() {
        return estado;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getExentaGMF() {
        return exentaGMF;
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public Date getFechaDeModificacion() {
        return fechaDeModificacion;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
