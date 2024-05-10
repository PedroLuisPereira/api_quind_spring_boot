package com.example.quind.domain.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.quind.domain.dto.ClienteSolicitud;
import com.example.quind.domain.exception.CampoConException;
import com.example.quind.domain.exception.RegistroNotFoundException;
import com.example.quind.domain.model.Cliente;
import com.example.quind.domain.model.Cuenta;
import com.example.quind.domain.ports.ClientePortRepository;
import com.example.quind.domain.ports.CuentaPortRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClientePortRepository clienteRepository;

    @Mock
    private CuentaPortRepository cuentaPortRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    ClienteSolicitud clienteSolicitud;
    private Date fechaNacimiento;

    @BeforeEach
    public void setup() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fechaNacimiento = new Date();
        try {
            fechaNacimiento = sdf.parse("2000-05-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cliente = Cliente.getInstance(1,
                "CC",
                "123456789",
                "Luis David",
                "Diaz Ruiz",
                "luis@gmail.com",
                fechaNacimiento,
                new Date(),
                new Date());

        clienteSolicitud = new ClienteSolicitud(
                0L,
                "CC",
                "123456789",
                "Luis David",
                "Diaz Ruiz",
                "luis@gmail.com",
                "2000-05-01");
    }

    @Test
    void listar() {

        Cliente cliente2 = Cliente.getInstance(2,
                "CC",
                "58485552",
                "Ana Maria",
                "Perez Ruiz",
                "ana@gmail.com",
                fechaNacimiento,
                new Date(),
                new Date());

        // given
        when(clienteRepository.listar()).thenReturn((List.of(cliente, cliente2)));

        // when - action or the behaviour that we are going test
        List<Cliente> clientes = clienteService.listar();

        // then - verify the output
        Assertions.assertNotNull(clientes);
        Assertions.assertEquals(2, clientes.size());
    }

    @Test
    void listarByid() {

        // given
        Mockito.when(clienteRepository.listarByid(1)).thenReturn(Optional.of(cliente));

        // when - action or the behaviour that we are going test
        Cliente cliente = clienteService.listarByid(1);

        // then - verify the output
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals("luis@gmail.com", cliente.getCorreoElectronico());

    }

    @Test
    void listarByidClienteNoEncontrado() {

        // given
        Mockito.when(clienteRepository.listarByid(anyLong())).thenReturn(Optional.empty());

        // when
        RegistroNotFoundException thrown = Assertions.assertThrows(RegistroNotFoundException.class,
                () -> clienteService.listarByid(anyLong()));

        // then
        Assertions.assertEquals("Cliente no encontrado", thrown.getMessage());
        Mockito.verify(clienteRepository, times(1)).listarByid(anyLong());
    }

    @Test
    void crear() {
        // given - precondition or setup
        Mockito.when(clienteRepository.guardar(any())).thenReturn(cliente);

        // when - action or the behaviour that we are going test
        Cliente clientCreate = clienteService.crear(clienteSolicitud);

        // then - verify the output
        Assertions.assertNotNull(clientCreate);
        Assertions.assertEquals("luis@gmail.com", clientCreate.getCorreoElectronico());
    }

    @Test
    void crearTipoDeIdentificacionNoValido() {

        // given
        clienteSolicitud.setTipoDeIdentificacion("OTRA");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.crear(clienteSolicitud));

        // then
        Assertions.assertEquals("El campo tipoDeIdentificacion no es valido, debe ser CC o CE", thrown.getMessage());
    }

    @Test
    void crearMenorDeEdad() {

        // given
        clienteSolicitud.setFechaDeNacimiento("2024-05-10");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.crear(clienteSolicitud));

        // then
        Assertions.assertEquals("Cliente es menor de edad", thrown.getMessage());
    }

    @Test
    void crearCorreoNoValido() {

        // given
        clienteSolicitud.setCorreoElectronico("otro");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.crear(clienteSolicitud));

        // then
        Assertions.assertEquals("Correo electrónico no es valido", thrown.getMessage());
    }

    @Test
    void crearNombreNoValido() {

        // given
        clienteSolicitud.setNombres("o");
        ;

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.crear(clienteSolicitud));

        // then
        Assertions.assertEquals("El campo nombres debe ser mayor de dos caracteres", thrown.getMessage());
    }

    @Test
    void crearApellidoNoValido() {

        // given
        clienteSolicitud.setApellidos("o");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.crear(clienteSolicitud));

        // then
        Assertions.assertEquals("El campo apellidos debe ser mayor de dos caracteres", thrown.getMessage());
    }

    @Test
    void crearFechaNacimientoNoValida() {

        // given
        clienteSolicitud.setFechaDeNacimiento("xxxx");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.crear(clienteSolicitud));

        // then
        Assertions.assertEquals("El campo fechaDeNacimiento no es valido, debe ser yyyy-mm-dd", thrown.getMessage());
    }

    @Test
    void actualizar() {
        // given - precondition or setup
        ClienteSolicitud clienteSolicitud = new ClienteSolicitud(
                1L,
                "CC",
                "58485552",
                "Ana Maria",
                "Perez Ruiz",
                "ana@gmail.com",
                "2000-05-01");

        Cliente cliente2 = Cliente.getInstance(1L,
                "CC",
                "58485552",
                "Ana Maria",
                "Perez Ruiz",
                "ana@gmail.com",
                fechaNacimiento,
                new Date(),
                new Date());

        Mockito.when(clienteRepository.listarByid(1L)).thenReturn(Optional.of(cliente));
        Mockito.when(clienteRepository.guardar(any())).thenReturn(cliente2);

        // when - action or the behaviour that we are going test
        Cliente clientUpdate = clienteService.actualizar(1L, clienteSolicitud);

        // then - verify the output
        Assertions.assertNotNull(clientUpdate);
        Assertions.assertEquals("ana@gmail.com", clientUpdate.getCorreoElectronico());
    }

    @Test
    void actualizarClienteNoExiste() {

        // given
        clienteSolicitud.setId(1L);

        // when
        Mockito.when(clienteRepository.listarByid(1L)).thenReturn(Optional.empty());

        RegistroNotFoundException thrown = Assertions.assertThrows(RegistroNotFoundException.class,
                () -> clienteService.actualizar(1L, clienteSolicitud));

        // then
        Assertions.assertEquals("Cliente no encontrado", thrown.getMessage());
    }

    @Test
    void actualizarTipoDeIdentificacionNoValido() {

        // given
        clienteSolicitud.setId(1L);
        clienteSolicitud.setTipoDeIdentificacion("OTRA");

        // when
        Mockito.when(clienteRepository.listarByid(1L)).thenReturn(Optional.of(cliente));

        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.actualizar(1L, clienteSolicitud));

        // then
        Assertions.assertEquals("El campo tipoDeIdentificacion no es valido, debe ser CC o CE", thrown.getMessage());
    }

    @Test
    void actualizarMenorDeEdad() {

        // given
        clienteSolicitud.setId(1L);
        clienteSolicitud.setFechaDeNacimiento("2024-05-10");

        // when
        Mockito.when(clienteRepository.listarByid(1L)).thenReturn(Optional.of(cliente));
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.actualizar(1L, clienteSolicitud));

        // then
        Assertions.assertEquals("Cliente es menor de edad", thrown.getMessage());
    }

    @Test
    void actualizarCorreoNoValido() {

        // given
        clienteSolicitud.setId(1L);
        clienteSolicitud.setCorreoElectronico("otro");

        // when
        Mockito.when(clienteRepository.listarByid(1L)).thenReturn(Optional.of(cliente));
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.actualizar(1L, clienteSolicitud));

        // then
        Assertions.assertEquals("Correo electrónico no es valido", thrown.getMessage());
    }

    @Test
    void actualizarNombreNoValido() {

        // given
        clienteSolicitud.setId(1L);
        clienteSolicitud.setNombres("o");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.actualizar(1L, clienteSolicitud));

        // then
        Assertions.assertEquals("El campo nombres debe ser mayor de dos caracteres", thrown.getMessage());
    }

    @Test
    void actualizarApellidoNoValido() {

        // given
        clienteSolicitud.setId(1L);
        clienteSolicitud.setApellidos("o");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.actualizar(1L, clienteSolicitud));

        // then
        Assertions.assertEquals("El campo apellidos debe ser mayor de dos caracteres", thrown.getMessage());
    }

    @Test
    void actualizarFechaNacimientoNoValida() {

        // given
        clienteSolicitud.setId(1L);
        clienteSolicitud.setFechaDeNacimiento("xxxx");

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.crear(clienteSolicitud));

        // then
        Assertions.assertEquals("El campo fechaDeNacimiento no es valido, debe ser yyyy-mm-dd", thrown.getMessage());
    }

    @Test
    void eliminar() {
        // given
        Mockito.when(cuentaPortRepository.listarByClienteId(1L)).thenReturn(new ArrayList<>());
        Mockito.doNothing().when(clienteRepository).eliminar(1L);

        // when
        clienteService.eliminar(1L);

        // then
        Mockito.verify(clienteRepository, Mockito.times(1)).eliminar(1L);
    }

    @Test
    void eliminarTieneProductos() {

        // given
        clienteSolicitud.setId(1L);
        Mockito.when(cuentaPortRepository.listarByClienteId(1L)).thenReturn(Arrays.asList(Cuenta.getInstance(
                1L,
                "CC",
                "3548",
                "Activa",
                5252.0,
                "SI",
                new Date(),
                new Date(),
                cliente)));

        // when
        CampoConException thrown = Assertions.assertThrows(CampoConException.class,
                () -> clienteService.eliminar(1L));

        // then
        Assertions.assertEquals("No se puede eliminar cliente debido a que tiene productos asociados",
                thrown.getMessage());
    }
}