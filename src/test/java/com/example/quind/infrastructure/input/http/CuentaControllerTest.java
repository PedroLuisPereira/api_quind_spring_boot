package com.example.quind.infrastructure.input.http;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.quind.application.cuenta.dto.CuentaDto;
import com.example.quind.application.cuenta.dto.CuentaModificarEstadoDto;
import com.example.quind.application.cuenta.dto.OperacionDto;
import com.example.quind.infrastructure.output.persistence.entity.ClienteEntity;
import com.example.quind.infrastructure.output.persistence.entity.CuentaEntity;
import com.example.quind.infrastructure.output.persistence.repository.ClienteRepository;
import com.example.quind.infrastructure.output.persistence.repository.CuentaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String ACTIVA = "Activa";
    private static final String CUENTA_AHORRO = "CUENTA_AHORRO";
    private ClienteEntity cliente;
    private CuentaEntity cuentaEntity;
    

    @BeforeEach
    void setup() throws ParseException {
        cuentaRepository.deleteAll();
        clienteRepository.deleteAll();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaNacimiento = sdf.parse("2000-05-01");

        cliente = new ClienteEntity(1L,
                "CC",
                "123456789",
                "Luis David",
                "Diaz Ruiz",
                "luis@gmail.com",
                fechaNacimiento,
                new Date(),
                new Date());
        cliente = clienteRepository.save(cliente);

        cuentaEntity = new CuentaEntity(1L,
                CUENTA_AHORRO,
                "5312345678",
                ACTIVA,
                100000,
                "SI",
                new Date(),
                new Date(),
                cliente);

    }

    @Test
    void list() throws Exception {

        // given - precondition or setup
        List<CuentaEntity> cuentaEntities = new ArrayList<>();
        cuentaEntities.add(cuentaEntity);
        cuentaRepository.saveAll(cuentaEntities);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/cuentas"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].numeroDeCuenta", is("5312345678")))
                .andExpect(jsonPath("$.size()", is(cuentaEntities.size())));
    }

    @Test
    void listByNumeroCuenta() throws Exception {
        // given - precondition or setup
        cuentaEntity = cuentaRepository.save(cuentaEntity);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/cuentas/numeroCuenta/5312345678"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.saldo", is(cuentaEntity.getSaldo())))
                .andExpect(jsonPath("$.tipoDeCuenta", is(cuentaEntity.getTipoDeCuenta())));
    }

    @Test
    void create() throws Exception {
        // given - precondition or setup
        CuentaDto cuentaDto = new CuentaDto(CUENTA_AHORRO, 100000, "SI", cliente.getId());

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuentaDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.saldo", is(cuentaDto.getSaldo())))
                .andExpect(jsonPath("$.tipoDeCuenta", is(cuentaDto.getTipoDeCuenta())));
    }

    @Test
    void modificarEstado() throws Exception {
        // given - precondition or setup
        cuentaEntity = cuentaRepository.save(cuentaEntity);
        CuentaModificarEstadoDto cuentaModificarEstadoDto = new CuentaModificarEstadoDto("5312345678", "Inactiva");

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/cuentas/operacion/estado")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuentaModificarEstadoDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is(cuentaModificarEstadoDto.getEstado())))
                .andExpect(jsonPath("$.numeroDeCuenta", is(cuentaModificarEstadoDto.getNumeroDeCuenta())));
    }

    @Test
    void consignar() throws Exception {
        // given - precondition or setup
        cuentaEntity = cuentaRepository.save(cuentaEntity);
        OperacionDto operacionDto = new OperacionDto(null, "5312345678", 50000);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/cuentas/operacion/consignar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operacionDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.saldo", is(150000.0)))
                .andExpect(jsonPath("$.numeroDeCuenta", is("5312345678")));

    }

    @Test
    void transferir() throws Exception {

        // given - precondition or setup
        cuentaEntity = cuentaRepository.save(cuentaEntity);

        cuentaRepository.save(new CuentaEntity(1L,
                CUENTA_AHORRO,
                "53223456789",
                ACTIVA,
                100000,
                "SI",
                new Date(),
                new Date(),
                cliente));

        OperacionDto operacionDto = new OperacionDto("5312345678", "53223456789", 50000);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/cuentas/operacion/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operacionDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.saldo", is(50000.0)))
                .andExpect(jsonPath("$.numeroDeCuenta", is("5312345678")));
    }

    @Test
    void retirar() throws Exception {
        // given - precondition or setup
        cuentaEntity = cuentaRepository.save(cuentaEntity);
        OperacionDto operacionDto = new OperacionDto("5312345678", null, 40000);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/cuentas/operacion/retirar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operacionDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.saldo", is(60000.0)))
                .andExpect(jsonPath("$.numeroDeCuenta", is("5312345678")));
    }
}