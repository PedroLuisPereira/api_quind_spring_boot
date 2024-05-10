package com.example.quind.application.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    private Long id;
    private String tipoDeIdentificacion;
    private String numeroDeIdentificacion;
    private String nombres;
    private String apellidos;
    private String correoElectronico;
    private String fechaDeNacimiento;

}
