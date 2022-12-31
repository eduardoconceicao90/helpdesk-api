package com.helpdeskapi.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdeskapi.domain.Chamado;
import com.helpdeskapi.domain.Cliente;
import com.helpdeskapi.domain.Tecnico;
import com.helpdeskapi.domain.dto.ChamadoDTO;
import com.helpdeskapi.domain.enums.Prioridade;
import com.helpdeskapi.domain.enums.Status;
import com.helpdeskapi.repository.ChamadoRepository;
import com.helpdeskapi.service.exception.ObjectNotFoundException;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Long id) {
		Optional<Chamado> obj = chamadoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + Chamado.class.getName()));
	}
	
	public List<Chamado> findAll(){
		return chamadoRepository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO objDTO) throws IllegalAccessException {
		return chamadoRepository.save(newChamado(objDTO));
	}
	
	private Chamado newChamado(ChamadoDTO obj) throws IllegalAccessException {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
		Chamado chamado = new Chamado();
		
		if(obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		
		return chamado;
	}

}
