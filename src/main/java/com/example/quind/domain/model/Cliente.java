package com.example.quind.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable {

    private final Long id;
    private final String tipoDeIdentificacion;
    private final String numeroDeIdentificacion;
    private final String nombres;
    private final String apellidos;
    private final String correoElectronico;
    private final Date fechaDeNacimiento;
    private final Date fechaDeCreacion;
    private final Date fechaDeModificacion;

    private Cliente(long id,
            String tipoDeIdentificacion,
            String numeroDeIdentificacion,
            String nombres,
            String apellidos,
            String correoElectronico,
            Date fechaDeNacimiento,
            Date fechaDeCreacion,
            Date fechaDeModificacion) {
        this.id = id;
        this.tipoDeIdentificacion = tipoDeIdentificacion;
        this.numeroDeIdentificacion = numeroDeIdentificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
    }

    public static Cliente getInstance(long id,
            String tipoDeIdentificacion,
            String numeroDeIdentificacion,
            String nombres,
            String apellidos,
            String correoElectronico,
            Date fechaDeNacimiento,
            Date fechaDeCreacion,
            Date fechaDeModificacion) {

        return new Cliente(
                id,
                tipoDeIdentificacion,
                numeroDeIdentificacion,
                nombres,
                apellidos,
                correoElectronico,
                fechaDeNacimiento,
                fechaDeCreacion,
                fechaDeModificacion);
    }

    public Long getId() {
        return id;
    }

    public String getTipoDeIdentificacion() {
        return tipoDeIdentificacion;
    }

    public String getNumeroDeIdentificacion() {
        return numeroDeIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public Date getFechaDeModificacion() {
        return fechaDeModificacion;
    }
}
