package com.example.quind.infrastructure.input.http;


import com.example.quind.application.cuenta.comando.*;
import com.example.quind.application.cuenta.consulta.CuentaListar;
import com.example.quind.application.cuenta.consulta.CuentaListarPorNumero;
import com.example.quind.application.cuenta.dto.CuentaDto;
import com.example.quind.application.cuenta.dto.CuentaModificarEstadoDto;
import com.example.quind.application.cuenta.dto.CuentaRespuestaDto;
import com.example.quind.application.cuenta.dto.OperacionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cuentas")
@CrossOrigin(origins = "*")
public class CuentaController {

    private final CuentaCrear cuentaCrear;
    private final CuentaConsignar cuentaConsignar;
    private final CuentaTransferir cuentaTransferir;
    private final CuentaRetirar cuentaRetirar;
    private final CuentaListar cuentaListar;
    private final CuentaListarPorNumero cuentaListarPorNumero;
    private final CuentaEstado cuentaEstado;


    public CuentaController(
            CuentaCrear cuentaCrear,
            CuentaConsignar cuentaConsignar,
            CuentaTransferir cuentaTransferir,
            CuentaRetirar cuentaRetirar,
            CuentaListar cuentaListar,
            CuentaListarPorNumero cuentaListarPorNumero,
            CuentaEstado cuentaEstado

    ) {
        this.cuentaCrear = cuentaCrear;
        this.cuentaConsignar = cuentaConsignar;
        this.cuentaTransferir = cuentaTransferir;
        this.cuentaRetirar = cuentaRetirar;
        this.cuentaListar = cuentaListar;
        this.cuentaListarPorNumero = cuentaListarPorNumero;
        this.cuentaEstado = cuentaEstado;
    }

    @GetMapping("")
    public List<CuentaRespuestaDto> list() {
        return cuentaListar.ejecutar();
    }

    @GetMapping("/numeroCuenta/{numeroCuenta}")
    public CuentaRespuestaDto listByNumeroCuenta(@PathVariable(value = "numeroCuenta") String numeroCuenta) {
        return cuentaListarPorNumero.ejecutar(numeroCuenta);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaRespuestaDto create(@RequestBody CuentaDto cuentaDto) {
        return cuentaCrear.ejecutar(cuentaDto);
    }

    @PostMapping("/operacion/estado")
    public CuentaRespuestaDto modificarEstado(@RequestBody CuentaModificarEstadoDto modificarEstadoDto) {
        return cuentaEstado.ejecutar(modificarEstadoDto);
    }

    @PostMapping("/operacion/consignar")
    public CuentaRespuestaDto consignar(@RequestBody OperacionDto operacionDto) {
        return cuentaConsignar.ejecutar(operacionDto);
    }

    @PostMapping("/operacion/transferir")
    public CuentaRespuestaDto transferir(@RequestBody OperacionDto operacionDto) {
        return cuentaTransferir.ejecutar(operacionDto);
    }

    @PostMapping("/operacion/retirar")
    public CuentaRespuestaDto retirar(@RequestBody OperacionDto operacionDto) {
        return cuentaRetirar.ejecutar(operacionDto);
    }

}
