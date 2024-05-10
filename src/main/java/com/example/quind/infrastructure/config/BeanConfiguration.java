package com.example.quind.infrastructure.config;

import com.example.quind.domain.ports.ClientePortRepository;
import com.example.quind.domain.ports.CuentaPortRepository;
import com.example.quind.domain.service.ClienteService;
import com.example.quind.domain.service.CuentaService;
import com.example.quind.infrastructure.output.persistence.ClientePersistenceAdapter;
import com.example.quind.infrastructure.output.persistence.CuentaPersistenceAdapter;
import com.example.quind.infrastructure.output.persistence.repository.ClienteRepository;
import com.example.quind.infrastructure.output.persistence.repository.CuentaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ClientePersistenceAdapter clientePersistenceAdapter(ClienteRepository clienteRepository) {
        return new ClientePersistenceAdapter(clienteRepository);
    }

    @Bean
    public ClienteService clienteService(ClientePortRepository clientePortRepository, CuentaPortRepository cuentaPortRepository ) {
        return new ClienteService(clientePortRepository, cuentaPortRepository );
    }

    @Bean
    public CuentaPersistenceAdapter cuentaPersistenceAdapter(CuentaRepository cuentaRepository) {
        return new CuentaPersistenceAdapter(cuentaRepository);
    }

    @Bean
    public CuentaService cuentaService(ClientePortRepository clientePortRepository, CuentaPortRepository cuentaPortRepository) {
        return new CuentaService(clientePortRepository,cuentaPortRepository);
    }
}