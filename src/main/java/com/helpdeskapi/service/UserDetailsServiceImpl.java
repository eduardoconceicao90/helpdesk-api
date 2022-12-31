package com.helpdeskapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.helpdeskapi.domain.Pessoa;
import com.helpdeskapi.repository.PessoaRepository;
import com.helpdeskapi.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Pessoa> user = pessoaRepository.findByEmail(email);
		
		if(user.isPresent()) {
			return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(), user.get().getPerfis());
		}
		
		throw new UsernameNotFoundException(email);
	}

}
