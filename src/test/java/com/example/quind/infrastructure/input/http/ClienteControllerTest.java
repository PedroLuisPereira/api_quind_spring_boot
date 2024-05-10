package com.example.quind.infrastructure.input.http;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.quind.domain.dto.ClienteSolicitud;
import com.example.quind.infrastructure.output.persistence.entity.ClienteEntity;
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
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ClienteEntity cliente;
    ClienteSolicitud clienteSolicitud;
    private Date fechaNacimiento;

    @BeforeEach
    void setup() throws ParseException {
        cuentaRepository.deleteAll();
        clienteRepository.deleteAll();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fechaNacimiento = sdf.parse("2000-05-01");

        cliente = new ClienteEntity(1L,
                "CC",
                "123456789",
                "Luis David",
                "Diaz Ruiz",
                "luis@gmail.com",
                fechaNacimiento,
                new Date(),
                new Date());

    }

    @Test
    void list() throws Exception {
        // given - precondition or setup
        List<ClienteEntity> clients = new ArrayList<>();
        clients.add(cliente);
        clienteRepository.saveAll(clients);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/clientes"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].correoElectronico", is("luis@gmail.com")))
                .andExpect(jsonPath("$.size()", is(clients.size())));
    }

    @Test
    void listByid() throws Exception {
        // given - precondition or setup
        cliente = clienteRepository.save(cliente);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/clientes/{id}", cliente.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.correoElectronico", is(cliente.getCorreoElectronico())))
                .andExpect(jsonPath("$.numeroDeIdentificacion", is(cliente.getNumeroDeIdentificacion())));
    }

    @Test
    void create() throws Exception {

        // given - precondition or setup

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroDeIdentificacion", is(cliente.getNumeroDeIdentificacion())))
                .andExpect(jsonPath("$.correoElectronico", is(cliente.getCorreoElectronico())));

    }

    @Test
    void update() throws Exception {

        // given - precondition or setup
        cliente = clienteRepository.save(cliente);

        ClienteEntity clienteUpdate = new ClienteEntity(1L,
                "CC",
                "123456789",
                "Luis",
                "Diaz",
                "luis@gmail.com",
                fechaNacimiento,
                new Date(),
                new Date());

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/clientes/{id}", cliente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteUpdate)));

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres", is(clienteUpdate.getNombres())))
                .andExpect(jsonPath("$.apellidos", is(clienteUpdate.getApellidos())));

    }

    @Test
    void deleteCliente() throws Exception{
        // given - precondition or setup
        cliente = clienteRepository.save(cliente);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/clientes/{id}", cliente.getId()));

        // then - verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}