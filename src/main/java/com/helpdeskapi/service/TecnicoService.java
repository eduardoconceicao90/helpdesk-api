package com.helpdeskapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdeskapi.domain.Pessoa;
import com.helpdeskapi.domain.Tecnico;
import com.helpdeskapi.domain.dto.TecnicoDTO;
import com.helpdeskapi.repository.PessoaRepository;
import com.helpdeskapi.repository.TecnicoRepository;
import com.helpdeskapi.service.exception.DataIntegratyViolationException;
import com.helpdeskapi.service.exception.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Tecnico findById(Long id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + Tecnico.class.getName()));
	}
	
	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}
	
	public Tecnico create(TecnicoDTO objDTO) {	
		objDTO.setId(null);
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return tecnicoRepository.save(newObj);
	}
	
	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");		
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj != null) {
			throw new DataIntegratyViolationException("Email já cadastrado!");		
		}
	}
}
