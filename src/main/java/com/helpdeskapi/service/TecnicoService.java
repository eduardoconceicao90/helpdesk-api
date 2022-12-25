package com.helpdeskapi.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
	
	public Tecnico update(Long id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		validaPorCpfEEmail(objDTO);		
		oldObj = new Tecnico(objDTO);		
		return tecnicoRepository.save(oldObj);
	}
	
	public void delete(Long id) {
		Tecnico obj = findById(id);	
		if (obj.getChamados().size() > 0){
			throw new DataIntegratyViolationException("Técnico possui ordens de serviços e não pode ser deletado!");
		}
		tecnicoRepository.deleteById(id);
	}
	
	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCPF(objDTO.getCpf());		
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");		
		}
				
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegratyViolationException("Email já cadastrado!");		
		}		
	}	
}
