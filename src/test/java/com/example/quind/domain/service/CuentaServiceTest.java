package com.example.quind.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.quind.domain.dto.CuentaEstadoDto;
import com.example.quind.domain.dto.CuentaSolicitud;
import com.example.quind.domain.dto.OperacionSolicitud;
import com.example.quind.domain.exception.CampoConException;
import com.example.quind.domain.exception.RegistroNotFoundException;
import com.example.quind.domain.model.Cliente;
import com.example.quind.domain.model.Cuenta;
import com.example.quind.domain.ports.ClientePortRepository;
import com.example.quind.domain.ports.CuentaPortRepository;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
class CuentaServiceTest {

    @Mock
    private ClientePortRepository clienteRepository;

    @Mock
    private CuentaPortRepository cuentaRepository;

    @InjectMocks
    private CuentaService cuentaService;

    private static final String ACTIVA = "Activa";
    private static final String CUENTA_AHORRO = "CUENTA_AHORRO";
    private static final String CUENTA_NO_ENCONTRADA = "Cuenta no encontrada";

    private Cliente cliente;
    private Cuenta cuenta;
    private CuentaSolicitud cuentaSolicitud;
    

    @BeforeEach
    public void setup() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaNacimiento = sdf.parse("2000-05-01");

        cliente = Cliente.getInstance(1,
                "CC",
                "123456789",
                "Luis David",
                "Diaz Ruiz",
                "luis@gmail.com",
                fechaNacimiento,
                new Date(),
                new Date());

        cuenta = Cuenta.getInstance(1L,
                CUENTA_AHORRO,
                "5312345678",
                ACTIVA,
                50000,
                "SI",
                new Date(),
                new Date(), cliente);

        cuentaSolicitud = new CuentaSolicitud(CUENTA_AHORRO, "50000", "SI", "1");
    }

    @Test
    void listar() {

        // given
        when(cuentaRepository.listar()).thenReturn((List.of(cuenta, cuenta)));

        // when - action or the behaviour that we are going test
        List<Cuenta> cuentas = cuentaService.listar();

        // then - verify the output
        Assertions.assertNotNull(cuentas);
        Assertions.assertEquals(2, cuentas.size());
    }

    @Test
    void listarByNumeroCuenta() {

        // given
        Mockito.when(cuentaRepository.listarByNumeroCuenta(anyString())).thenReturn(List.of(cuenta));

        // when - action or the behaviour that we are going test
        Cuenta cuenta = cuentaService.listarByNumeroCuenta("5312345678");

        // then - verify the output
        Assertions.assertNotNull(cuenta);
        Assertions.assertEquals(50000, cuenta.getSaldo());
    }

    @Test
    void crear() {
        // given - precondition or setup
        Mockito.when(clienteRepository.listarByid(1L)).thenReturn(Optional.of(cliente));
        Mockito.when(cuentaRepository.guardar(any())).thenReturn(cuenta);

        // when - action or the behaviour that we are going test
        Cuenta cuentaCrear = cuentaService.crear(cuentaSolicitud);

        // then - verify the output
        Assertions.assertNotNull(cuentaCrear);
        Assertions.assertEquals(50000, cuentaCrear.getSaldo());
        Assertions.assertEquals("luis@gmail.com", cuentaCrear.getCliente().getCorreoElectronico());
    }

    @Test
    void crearTipoDeCuentaNoValido() {

        // given
        cuentaSolicitud.setTipoDeCuenta("OTRA");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> cuentaService.crear(cuentaSolicitud));

        // then
        Assertions.assertEquals("El campo tipoDeCuenta debe ser CUENTA_AHORRO o CUENTA_CORRIENTE", thrown.getMessage());
    }

    @Test
    void crearSaldoNegativoAhorro() {

        // given
        cuentaSolicitud.setSaldo("-50000");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> cuentaService.crear(cuentaSolicitud));

        // then
        Assertions.assertEquals("El saldo de CUENTA_AHORRO no puede ser menor a 0", thrown.getMessage());
    }

    @Test
    void crearTipoExentaGMF() {

        // given
        cuentaSolicitud.setExentaGMF("OTRO");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> cuentaService.crear(cuentaSolicitud));

        // then
        Assertions.assertEquals("El campo exentaGMF debe ser SI o NO", thrown.getMessage());
    }

    @Test
    void crearClienteNoExiste() {

        // given
        cuentaSolicitud.setClienteId("5");
        Mockito.when(clienteRepository.listarByid(anyLong())).thenReturn(Optional.empty());

        // when
        RegistroNotFoundException thrown = Assertions.assertThrows(RegistroNotFoundException.class,
                () -> cuentaService.crear(cuentaSolicitud));

        // then
        Assertions.assertEquals("Cliente no encontrado", thrown.getMessage());
    }

    @Test
    void modificarEstado() {
        // given - precondition or setup
        Cuenta cuenta2 = Cuenta.getInstance(1L,
                CUENTA_AHORRO,
                "5312345678",
                "Inactiva",
                50000,
                "SI",
                new Date(),
                new Date(), cliente);

        Mockito.when(cuentaRepository.listarByNumeroCuenta(anyString())).thenReturn(List.of(cuenta));
        Mockito.when(cuentaRepository.guardar(any())).thenReturn(cuenta2);

        // when - action or the behaviour that we are going test
        Cuenta cuentaEstado = cuentaService.modificarEstado(new CuentaEstadoDto("123456789", "Inactiva"));

        // then - verify the output
        Assertions.assertNotNull(cuentaEstado);
        Assertions.assertEquals(50000, cuentaEstado.getSaldo());
        Assertions.assertEquals("Inactiva", cuentaEstado.getEstado());
        Assertions.assertEquals("luis@gmail.com", cuentaEstado.getCliente().getCorreoElectronico());
    }

    @Test
    void modificarEstadoErrado() {

        // given
        CuentaEstadoDto cuentaEstadoDto = new CuentaEstadoDto("5312345678", "OTRO");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> cuentaService.modificarEstado(cuentaEstadoDto));

        // then
        Assertions.assertEquals("Estado no valido, debe ser Activa, Inactiva, Cancelada", thrown.getMessage());
    }

    @Test
    void modificarEstadoCuentaNoExiste() {

        // given
        CuentaEstadoDto cuentaEstadoDto = new CuentaEstadoDto("5312345678", "Activa");
        Mockito.when(cuentaRepository.listarByNumeroCuenta(anyString())).thenReturn(new ArrayList<>());

        // when
        RegistroNotFoundException thrown = Assertions.assertThrows(RegistroNotFoundException.class,
                () -> cuentaService.modificarEstado(cuentaEstadoDto));

        // then
        Assertions.assertEquals(CUENTA_NO_ENCONTRADA, thrown.getMessage());
    }

    @Test
    void modificarEstadoSaldoCeroAhorro() {

        // given
        CuentaEstadoDto cuentaEstadoDto = new CuentaEstadoDto("5312345678", "Cancelada");
        Mockito.when(cuentaRepository.listarByNumeroCuenta(anyString())).thenReturn(List.of(cuenta));

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> cuentaService.modificarEstado(cuentaEstadoDto));

        // then
        Assertions.assertEquals("No se puede cancelar cuenta debe tener saldo en 0", thrown.getMessage());
    }

    @Test
    void consignacion() {
        // given - precondition or setup
        Cuenta cuenta2 = Cuenta.getInstance(1L,
                CUENTA_AHORRO,
                "5312345678",
                "Activa",
                100000,
                "SI",
                new Date(),
                new Date(),
                cliente);

        Mockito.when(cuentaRepository.listarByNumeroCuenta(anyString())).thenReturn(List.of(cuenta));
        Mockito.when(cuentaRepository.guardar(any())).thenReturn(cuenta2);

        // when - action or the behaviour that we are going test
        Cuenta cuentaUpdate = cuentaService.consignacion(new OperacionSolicitud(null, "123456789", "50000"));

        // then - verify the output
        Assertions.assertNotNull(cuentaUpdate);
        Assertions.assertEquals(100000, cuentaUpdate.getSaldo());
        Assertions.assertEquals("Activa", cuentaUpdate.getEstado());
        Assertions.assertEquals("luis@gmail.com", cuentaUpdate.getCliente().getCorreoElectronico());
    }

    @Test
    void consignacionCuentaNoExiste() {

        // given
        OperacionSolicitud operacionSolicitud = new OperacionSolicitud(null, "5312345678", "50000");
        Mockito.when(cuentaRepository.listarByNumeroCuenta(anyString())).thenReturn(new ArrayList<>());

        // when
        RegistroNotFoundException thrown = Assertions.assertThrows(RegistroNotFoundException.class,
                () -> cuentaService.consignacion(operacionSolicitud));

        // then
        Assertions.assertEquals(CUENTA_NO_ENCONTRADA, thrown.getMessage());
    }

    @Test
    void transferencia() {
        // given - precondition or setup
        Cuenta cuentaOrigenUpdate = Cuenta.getInstance(1L,
                CUENTA_AHORRO,
                "5312345678",
                ACTIVA,
                0,
                "SI",
                new Date(),
                new Date(), cliente);

        Cuenta cuentaDestino = Cuenta.getInstance(2L,
                CUENTA_AHORRO,
                "5387654321",
                "Activa",
                100000,
                "SI",
                new Date(),
                new Date(),
                cliente);

        Cuenta cuentaDestinoUpdate = Cuenta.getInstance(2L,
                CUENTA_AHORRO,
                "5387654321",
                "Activa",
                150000,
                "SI",
                new Date(),
                new Date(),
                cliente);

        Mockito.when(cuentaRepository.listarByNumeroCuenta(anyString())).thenReturn(List.of(cuenta));
        Mockito.when(cuentaRepository.listarByNumeroCuenta(anyString())).thenReturn(List.of(cuentaDestino));
        Mockito.when(cuentaRepository.guardar(any())).thenReturn(cuentaDestinoUpdate);
        Mockito.when(cuentaRepository.guardar(any())).thenReturn(cuentaOrigenUpdate);

        // when - action or the behaviour that we are going test
        Cuenta cuentaUpdate = cuentaService.transferencia(new OperacionSolicitud("5312345678", "5387654321", "50000"));

        // then - verify the output
        Assertions.assertNotNull(cuentaUpdate);
        Assertions.assertEquals(0, cuentaUpdate.getSaldo());
        Assertions.assertEquals("Activa", cuentaUpdate.getEstado());
        Assertions.assertEquals("luis@gmail.com", cuentaUpdate.getCliente().getCorreoElectronico());
    }

    @Test
    void transferenciaCuentaOrigenNoexiste() {

        // given
        OperacionSolicitud operacionSolicitud = new OperacionSolicitud("5312345678", "5322345678", "50000");
        Mockito.when(cuentaRepository.listarByNumeroCuenta("5312345678")).thenReturn(new ArrayList<>());
        

        // when
        RegistroNotFoundException thrown = Assertions.assertThrows(RegistroNotFoundException.class,
                () -> cuentaService.transferencia(operacionSolicitud));

        // then
        Assertions.assertEquals("Cuenta origen no encontrada", thrown.getMessage());
    }

    @Test
    void transferenciaCuentaDestinoNoexiste() {

        // given
        OperacionSolicitud operacionSolicitud = new OperacionSolicitud("5312345678", "5345678963", "50000");
        Mockito.when(cuentaRepository.listarByNumeroCuenta("5312345678")).thenReturn(List.of(cuenta));
        Mockito.when(cuentaRepository.listarByNumeroCuenta("5345678963")).thenReturn(new ArrayList<>());
        

        // when
        RegistroNotFoundException thrown = Assertions.assertThrows(RegistroNotFoundException.class,
                () -> cuentaService.transferencia(operacionSolicitud));

        // then
        Assertions.assertEquals("Cuenta destino no encontrada", thrown.getMessage());
    }

    @Test
    void transferenciaSaldoInsufuciente() {

        // given
        Cuenta cuentaDestino = Cuenta.getInstance(2L,
                CUENTA_AHORRO,
                "5387654321",
                "Activa",
                100000,
                "SI",
                new Date(),
                new Date(),
                cliente);

        OperacionSolicitud operacionSolicitud = new OperacionSolicitud("5312345678", "5387654321", "60000");
        Mockito.when(cuentaRepository.listarByNumeroCuenta("5312345678")).thenReturn(List.of(cuenta));
        Mockito.when(cuentaRepository.listarByNumeroCuenta("5387654321")).thenReturn(List.of(cuentaDestino));
        

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> cuentaService.transferencia(operacionSolicitud));

        // then
        Assertions.assertEquals("No se puede realizar operacion saldo insuficiente", thrown.getMessage());
    }

    @Test
    void retiro() {
        // given - precondition or setup
        Cuenta cuentaOrigenUpdate = Cuenta.getInstance(1L,
                CUENTA_AHORRO,
                "5312345678",
                ACTIVA,
                0,
                "SI",
                new Date(),
                new Date(), cliente);

        Mockito.when(cuentaRepository.listarByNumeroCuenta(anyString())).thenReturn(List.of(cuenta));
        Mockito.when(cuentaRepository.guardar(any())).thenReturn(cuentaOrigenUpdate);

        // when - action or the behaviour that we are going test
        Cuenta cuentaUpdate = cuentaService.retiro(new OperacionSolicitud("5312345678", null, "50000"));

        // then - verify the output
        Assertions.assertNotNull(cuentaUpdate);
        Assertions.assertEquals(0, cuentaUpdate.getSaldo());
        Assertions.assertEquals("Activa", cuentaUpdate.getEstado());
        Assertions.assertEquals("luis@gmail.com", cuentaUpdate.getCliente().getCorreoElectronico());
    }

    @Test
    void retiroCuentaNoExiste() {

        // given
        OperacionSolicitud operacionSolicitud = new OperacionSolicitud("5312345678", null, "60000");
        Mockito.when(cuentaRepository.listarByNumeroCuenta("5312345678")).thenReturn(new ArrayList<>());
        

        // when
        RegistroNotFoundException thrown = Assertions.assertThrows(RegistroNotFoundException.class,
                () -> cuentaService.retiro(operacionSolicitud));

        // then
        Assertions.assertEquals("Cuenta origen no encontrada", thrown.getMessage());
    }

    @Test
    void retiroSaldoInsuficiente() {

        // given
        OperacionSolicitud operacionSolicitud = new OperacionSolicitud("5312345678", null, "60000");
        Mockito.when(cuentaRepository.listarByNumeroCuenta("5312345678")).thenReturn(List.of(cuenta));
        

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> cuentaService.retiro(operacionSolicitud));

        // then
        Assertions.assertEquals("No se puede realizar operacion saldo insuficiente", thrown.getMessage());
    }
}