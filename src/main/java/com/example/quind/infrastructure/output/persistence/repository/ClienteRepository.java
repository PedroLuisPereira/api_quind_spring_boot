package com.example.quind.infrastructure.output.persistence.repository;

import com.example.quind.infrastructure.output.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

}