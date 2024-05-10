package com.example.quind.application.cuenta;


import com.example.quind.application.cuenta.dto.CuentaDto;
import com.example.quind.application.cuenta.dto.CuentaModificarEstadoDto;
import com.example.quind.application.cuenta.dto.CuentaRespuestaDto;
import com.example.quind.application.cuenta.dto.OperacionDto;
import com.example.quind.domain.dto.CuentaEstadoDto;
import com.example.quind.domain.dto.CuentaSolicitud;
import com.example.quind.domain.dto.OperacionSolicitud;
import com.example.quind.domain.model.Cuenta;


public class CuentaTransformador {

    private CuentaTransformador() {
    }

    public static CuentaSolicitud transformar(CuentaDto cuentaDto) {
        return new CuentaSolicitud(
                cuentaDto.getTipoDeCuenta(),
                cuentaDto.getSaldo(),
                cuentaDto.getExentaGMF(),
                cuentaDto.getClienteId()
        );
    }

    public static OperacionSolicitud transformar(OperacionDto operacionDto) {
        return new OperacionSolicitud(
                operacionDto.getNumeroCuentaOrigen(),
                operacionDto.getNumeroCuentaDestino(),
                operacionDto.getValor()
        );
    }

    public static CuentaEstadoDto transformar(CuentaModificarEstadoDto modificarEstadoDto) {
        return new CuentaEstadoDto(
                modificarEstadoDto.getNumeroDeCuenta(),
                modificarEstadoDto.getEstado()
        );
    }


    public static CuentaRespuestaDto transformar(Cuenta cuenta) {
        return new CuentaRespuestaDto(
                cuenta.getId(),
                cuenta.getTipoDeCuenta(),
                cuenta.getNumeroDeCuenta(),
                cuenta.getEstado(),
                cuenta.getSaldo(),
                cuenta.getExentaGMF(),
                cuenta.getFechaDeCreacion().toString(),
                cuenta.getFechaDeModificacion().toString()
        );
    }
}
