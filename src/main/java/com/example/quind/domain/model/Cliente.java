package com.example.quind.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable {

    private Long id;
    private String tipoDeIdentificacion;
    private String numeroDeIdentificacion;
    private String nombres;
    private String apellidos;
    private String correoElectronico;
    private Date fechaDeNacimiento;
    private Date fechaDeCreacion;
    private Date fechaDeModificacion;

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
