package com.example.quind.infrastructure.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuentas")
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_de_cuenta")
    private String tipoDeCuenta;

    @Column(name = "numero_de_cuenta", unique = true)
    private String numeroDeCuenta;

    private String estado;

    private double saldo;

    @Column(name = "exenta_GMF")
    private String exentaGMF;

    @Column(name = "fecha_de_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeCreacion;

    @Column(name = "fecha_de_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeModificacion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ClienteEntity clienteEntity;

}