package com.helpdeskapi.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.helpdeskapi.domain.Cliente;
import com.helpdeskapi.domain.Pessoa;
import com.helpdeskapi.domain.dto.ClienteDTO;
import com.helpdeskapi.repository.ClienteRepository;
import com.helpdeskapi.repository.PessoaRepository;
import com.helpdeskapi.service.exception.DataIntegratyViolationException;
import com.helpdeskapi.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Cliente findById(Long id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Cliente create(ClienteDTO objDTO) {	
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return clienteRepository.save(newObj);
	}
	
	public Cliente update(Long id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) {
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		}
		
		validaPorCpfEEmail(objDTO);		
		oldObj = new Cliente(objDTO);		
		return clienteRepository.save(oldObj);
	}
	
	public void delete(Long id) {
		Cliente obj = findById(id);	
		if (obj.getChamados().size() > 0){
			throw new DataIntegratyViolationException("Cliente possui ordens de serviços e não pode ser deletado!");
		}
		clienteRepository.deleteById(id);
	}
	
	private void validaPorCpfEEmail(ClienteDTO objDTO) {
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
