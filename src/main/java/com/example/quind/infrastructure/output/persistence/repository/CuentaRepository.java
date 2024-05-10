package com.example.quind.infrastructure.output.persistence.repository;

import com.example.quind.infrastructure.output.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CuentaRepository extends JpaRepository<CuentaEntity, Long> {

    List<CuentaEntity> findByNumeroDeCuenta(String numeroCuenta);

    @Query(value = "SELECT * FROM cuentas c WHERE c.cliente_entity_id = ?1", nativeQuery = true)
    List<CuentaEntity> findByClienteId(Long clienteId);
}