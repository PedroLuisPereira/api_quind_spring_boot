package com.example.quind.application.cuenta.comando;

import com.example.quind.application.cuenta.CuentaTransformador;
import com.example.quind.application.cuenta.dto.CuentaModificarEstadoDto;
import com.example.quind.application.cuenta.dto.CuentaRespuestaDto;
import com.example.quind.domain.dto.CuentaEstadoDto;
import com.example.quind.domain.model.Cuenta;
import com.example.quind.domain.service.CuentaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CuentaEstado {

    private final CuentaService cuentaService;

    public CuentaEstado(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @Transactional
    public CuentaRespuestaDto ejecutar(CuentaModificarEstadoDto modificarEstadoDto) {
        CuentaEstadoDto cuentaEstadoDto = CuentaTransformador.transformar(modificarEstadoDto);
        Cuenta cuenta = cuentaService.modificarEstado(cuentaEstadoDto);
        return CuentaTransformador.transformar(cuenta);
    }
}
