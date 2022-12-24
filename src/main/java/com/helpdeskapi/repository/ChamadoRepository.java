package com.helpdeskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpdeskapi.domain.Chamado;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long>{

}
