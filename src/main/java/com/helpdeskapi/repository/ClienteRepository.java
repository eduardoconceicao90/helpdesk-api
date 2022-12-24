package com.helpdeskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpdeskapi.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
