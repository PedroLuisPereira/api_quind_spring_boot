package com.example.quind.application.cuenta.comando;

import com.example.quind.application.cuenta.CuentaTransformador;
import com.example.quind.application.cuenta.dto.CuentaRespuestaDto;
import com.example.quind.application.cuenta.dto.OperacionDto;
import com.example.quind.domain.dto.OperacionSolicitud;
import com.example.quind.domain.model.Cuenta;
import com.example.quind.domain.service.CuentaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CuentaRetirar {

    private final CuentaService cuentaService;

    public CuentaRetirar(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @Transactional
    public CuentaRespuestaDto ejecutar(OperacionDto operacionDto) {
        OperacionSolicitud operacionSolicitud = CuentaTransformador.transformar(operacionDto);
        Cuenta cuenta = cuentaService.retiro(operacionSolicitud);
        return CuentaTransformador.transformar(cuenta);
    }
}
