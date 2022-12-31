package com.helpdeskapi.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.helpdeskapi.domain.Chamado;
import com.helpdeskapi.domain.Cliente;
import com.helpdeskapi.domain.Tecnico;
import com.helpdeskapi.domain.enums.Perfil;
import com.helpdeskapi.domain.enums.Prioridade;
import com.helpdeskapi.domain.enums.Status;
import com.helpdeskapi.repository.ChamadoRepository;
import com.helpdeskapi.repository.ClienteRepository;
import com.helpdeskapi.repository.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instanciaDB() {
		
		Tecnico tecnico1 = new Tecnico(null, "Eduardo Conceição", "591.212.220-45", "eduardo@mail.com", encoder.encode("123"));
		tecnico1.addPerfil(Perfil.ADMIN);
		
		Tecnico tecnico2 = new Tecnico(null, "Ronaldo Bulhões", "177.504.790-31", "ronaldo@mail.com", encoder.encode("123"));
		tecnico1.addPerfil(Perfil.TECNICO);
		
		Cliente cliente1 = new Cliente(null, "Telma Souza", "794.659.920-03", "telma@mail.com", encoder.encode("123"));
		Cliente cliente2 = new Cliente(null, "Gabriel Souza", "844.493.930-74", "gabriel@mail.com", encoder.encode("123"));
				
		Chamado chamado1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Trocar fonte do notebook", tecnico1, cliente1);
		Chamado chamado2 = new Chamado(null, Prioridade.BAIXA, Status.ABERTO, "Chamado 02", "Leitor com problema", tecnico1, cliente2);
		
		tecnicoRepository.saveAll(Arrays.asList(tecnico1, tecnico2));
		clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
		chamadoRepository.saveAll(Arrays.asList(chamado1, chamado2));
	}
}
