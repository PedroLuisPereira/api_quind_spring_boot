package com.example.quind.application.cuenta.consulta;


import com.example.quind.application.cuenta.CuentaTransformador;
import com.example.quind.application.cuenta.dto.CuentaRespuestaDto;
import com.example.quind.domain.service.CuentaService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CuentaListarPorNumero {

    private final CuentaService cuentaService;

    public CuentaListarPorNumero(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @Transactional(readOnly = true)
    public CuentaRespuestaDto ejecutar(String numeroCuenta) {
        return CuentaTransformador.transformar(cuentaService.listarByNumeroCuenta(numeroCuenta));
    }
}
