package com.example.quind.application.cuenta.consulta;


import com.example.quind.application.cuenta.CuentaTransformador;
import com.example.quind.application.cuenta.dto.CuentaRespuestaDto;
import com.example.quind.domain.service.CuentaService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class CuentaListar {

    private final CuentaService cuentaService;

    public CuentaListar(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @Transactional(readOnly = true)
    public List<CuentaRespuestaDto> ejecutar() {

        return cuentaService.listar()
                .stream()
                .map(CuentaTransformador::transformar)
                .toList();
    }


}
