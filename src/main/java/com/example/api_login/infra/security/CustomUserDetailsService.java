package com.example.api_login.infra.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.api_login.model.entities.User;
import com.example.api_login.repositories.IUserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired	
	private IUserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		User user = this.userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails
				.User(user.getEmail(), user.getSenha(), new ArrayList<>());
	}
	

}
