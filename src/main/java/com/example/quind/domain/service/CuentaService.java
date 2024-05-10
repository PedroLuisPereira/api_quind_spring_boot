package com.example.quind.domain.service;

import com.example.quind.domain.dto.CuentaEstadoDto;
import com.example.quind.domain.dto.CuentaSolicitud;
import com.example.quind.domain.dto.OperacionSolicitud;
import com.example.quind.domain.exception.CampoConException;
import com.example.quind.domain.exception.RegistroNotFoundException;
import com.example.quind.domain.model.Cliente;
import com.example.quind.domain.model.Cuenta;
import com.example.quind.domain.ports.ClientePortRepository;
import com.example.quind.domain.ports.CuentaPortRepository;
import com.example.quind.domain.validation.Validacion;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class CuentaService {

    private final ClientePortRepository clienteRepository;
    private final CuentaPortRepository cuentaRepository;

    private static final String ACTIVA = "Activa";
    private static final String CUENTA_AHORRO = "CUENTA_AHORRO";
    private static final String CUENTA_CORRIENTE = "CUENTA_CORRIENTE";
    private static final String CUENTA_NO_ENCONTRADA = "Cuenta no encontrada";
    private static final Random RANDOM = new Random();

    public CuentaService(ClientePortRepository clienteRepository, CuentaPortRepository cuentaRepository) {
        this.clienteRepository = clienteRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public List<Cuenta> listar() {
        return cuentaRepository.listar();
    }

    public Cuenta listarByNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.listarByNumeroCuenta(numeroCuenta).stream().findFirst()
                .orElseThrow(() -> new RegistroNotFoundException(CUENTA_NO_ENCONTRADA));
    }

    public Cuenta crear(CuentaSolicitud cuentaSolicitud) {

        Validacion.validarObligatorio(cuentaSolicitud.getTipoDeCuenta(), "El campo tipoDeCuenta es requerido");
        Validacion.validarObligatorio(cuentaSolicitud.getSaldo(), "El campo saldo es requerido");
        Validacion.validarObligatorio(cuentaSolicitud.getExentaGMF(), "El campo exentaGMF es requerido");
        Validacion.validarObligatorio(cuentaSolicitud.getClienteId(), "El campo clienteId es requerido");
        Validacion.validarValorNumericoDouble(cuentaSolicitud.getSaldo(), "El campo saldo debe ser numérico");
        Validacion.validarValorNumericoLong(cuentaSolicitud.getClienteId(), "El campo clienteId debe ser numérico");

        double saldo = Double.parseDouble(cuentaSolicitud.getSaldo());
        long clienteId = Long.parseLong(cuentaSolicitud.getClienteId());

        if (!cuentaSolicitud.getTipoDeCuenta().equals(CUENTA_AHORRO)
                && !cuentaSolicitud.getTipoDeCuenta().equals(CUENTA_CORRIENTE)) {
            throw new CampoConException("El campo tipoDeCuenta debe ser CUENTA_AHORRO o CUENTA_CORRIENTE");
        }

        String numeroCuenta = getNumeroCuenta(8);
        if (cuentaSolicitud.getTipoDeCuenta().equals(CUENTA_AHORRO)) {
            numeroCuenta = "53" + numeroCuenta;
        } else {
            numeroCuenta = "33" + numeroCuenta;
        }

        if (cuentaSolicitud.getTipoDeCuenta().equals(CUENTA_AHORRO) && saldo < 0) {
            throw new CampoConException("El saldo de CUENTA_AHORRO no puede ser menor a 0");
        }

        if (!cuentaSolicitud.getExentaGMF().equals("SI") && !cuentaSolicitud.getExentaGMF().equals("NO")) {
            throw new CampoConException("El campo exentaGMF debe ser SI o NO");
        }

        Cliente cliente = clienteRepository.listarByid(clienteId)
                .orElseThrow(() -> new RegistroNotFoundException("Cliente no encontrado"));

        Cuenta cuenta = Cuenta.getInstance(
                0L,
                cuentaSolicitud.getTipoDeCuenta(),
                numeroCuenta,
                ACTIVA,
                saldo,
                cuentaSolicitud.getExentaGMF(),
                new Date(),
                new Date(),
                cliente);

        return cuentaRepository.guardar(cuenta);
    }

    public Cuenta modificarEstado(CuentaEstadoDto cuentaEstadoDto) {

        Validacion.validarObligatorio(cuentaEstadoDto.getNumeroDeCuenta(), "El campo numeroDeCuenta es requerido");
        Validacion.validarObligatorio(cuentaEstadoDto.getEstado(), "El campo estado es requerido");

        if (!cuentaEstadoDto.getEstado().equals(ACTIVA)
                && !cuentaEstadoDto.getEstado().equals("Inactiva")
                && !cuentaEstadoDto.getEstado().equals("Cancelada")) {
            throw new CampoConException("Estado no valido, debe ser Activa, Inactiva, Cancelada");
        }

        Cuenta cuenta = cuentaRepository.listarByNumeroCuenta(cuentaEstadoDto.getNumeroDeCuenta())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RegistroNotFoundException(CUENTA_NO_ENCONTRADA));

        if (cuentaEstadoDto.getEstado().equals("Cancelada") && cuenta.getSaldo() != 0) {
            throw new CampoConException("No se puede cancelar cuenta debe tener saldo en 0");
        }

        return cuentaRepository.guardar(
                Cuenta.getInstance(
                        cuenta.getId(),
                        cuenta.getTipoDeCuenta(),
                        cuenta.getNumeroDeCuenta(),
                        cuentaEstadoDto.getEstado(),
                        cuenta.getSaldo(),
                        cuenta.getExentaGMF(),
                        cuenta.getFechaDeCreacion(),
                        new Date(),
                        cuenta.getCliente()));
    }

    public Cuenta consignacion(OperacionSolicitud operacionSolicitud) {

        Cuenta cuentaActual = cuentaRepository.listarByNumeroCuenta(operacionSolicitud.getNumeroCuentaDestino())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RegistroNotFoundException(CUENTA_NO_ENCONTRADA));

        Cuenta cuenta = Cuenta.getInstance(
                cuentaActual.getId(),
                cuentaActual.getTipoDeCuenta(),
                cuentaActual.getNumeroDeCuenta(),
                cuentaActual.getEstado(),
                cuentaActual.getSaldo() + operacionSolicitud.getValor(),
                cuentaActual.getExentaGMF(),
                cuentaActual.getFechaDeCreacion(),
                new Date(),
                cuentaActual.getCliente());

        return cuentaRepository.guardar(cuenta);

    }

    public Cuenta transferencia(OperacionSolicitud operacionSolicitud) {

        Cuenta cuentaOrigen = cuentaRepository.listarByNumeroCuenta(operacionSolicitud.getNumeroCuentaOrigen()).stream()
                .findFirst()
                .orElseThrow(() -> new RegistroNotFoundException("Cuenta origen no encontrada"));

        Cuenta cuentaDestino = cuentaRepository.listarByNumeroCuenta(operacionSolicitud.getNumeroCuentaDestino())
                .stream().findFirst()
                .orElseThrow(() -> new RegistroNotFoundException("Cuenta destino no encontrada"));

        if (cuentaOrigen.getSaldo() < operacionSolicitud.getValor()
                && !cuentaOrigen.getTipoDeCuenta().equals(CUENTA_CORRIENTE)) {
            throw new CampoConException("No se puede realizar operacion saldo insuficiente");
        }

        cuentaOrigen = Cuenta.getInstance(
                cuentaOrigen.getId(),
                cuentaOrigen.getTipoDeCuenta(),
                cuentaOrigen.getNumeroDeCuenta(),
                cuentaOrigen.getEstado(),
                cuentaOrigen.getSaldo() - operacionSolicitud.getValor(),
                cuentaOrigen.getExentaGMF(),
                cuentaOrigen.getFechaDeCreacion(),
                new Date(),
                cuentaOrigen.getCliente());

        cuentaDestino = Cuenta.getInstance(
                cuentaDestino.getId(),
                cuentaDestino.getTipoDeCuenta(),
                cuentaDestino.getNumeroDeCuenta(),
                cuentaDestino.getEstado(),
                cuentaDestino.getSaldo() + operacionSolicitud.getValor(),
                cuentaDestino.getExentaGMF(),
                cuentaDestino.getFechaDeCreacion(),
                new Date(),
                cuentaDestino.getCliente());

        cuentaRepository.guardar(cuentaDestino);
        return cuentaRepository.guardar(cuentaOrigen);

    }

    public Cuenta retiro(OperacionSolicitud operacionSolicitud) {

        Cuenta cuentaOrigen = cuentaRepository.listarByNumeroCuenta(operacionSolicitud.getNumeroCuentaOrigen()).stream()
                .findFirst()
                .orElseThrow(() -> new RegistroNotFoundException("Cuenta origen no encontrada"));

        if (cuentaOrigen.getSaldo() < operacionSolicitud.getValor()
                && !cuentaOrigen.getTipoDeCuenta().equals(CUENTA_CORRIENTE)) {
            throw new CampoConException("No se puede realizar operacion saldo insuficiente");
        }

        cuentaOrigen = Cuenta.getInstance(
                cuentaOrigen.getId(),
                cuentaOrigen.getTipoDeCuenta(),
                cuentaOrigen.getNumeroDeCuenta(),
                cuentaOrigen.getEstado(),
                cuentaOrigen.getSaldo() - operacionSolicitud.getValor(),
                cuentaOrigen.getExentaGMF(),
                cuentaOrigen.getFechaDeCreacion(),
                new Date(),
                cuentaOrigen.getCliente());

        return cuentaRepository.guardar(cuentaOrigen);

    }

    private String getNumeroCuenta(int i) {
        String numeros = "0123456789";
        StringBuilder builder;
        builder = new StringBuilder(i);

        for (int m = 0; m < i; m++) {
            int index = RANDOM.nextInt(numeros.length());
            builder.append(numeros.charAt(index));
        }

        return builder.toString();
    }

}
