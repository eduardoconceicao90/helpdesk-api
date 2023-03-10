package com.helpdeskapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.helpdeskapi.domain.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	@Query("SELECT obj FROM Pessoa obj WHERE obj.cpf =:cpf")
	Optional<Pessoa> findByCPF(@Param(value = "cpf") String cpf);

	@Query("SELECT obj FROM Pessoa obj WHERE obj.email =:email")
	Optional<Pessoa> findByEmail(@Param(value = "email") String email);
}
