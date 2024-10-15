package com.example.api_login.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Com os Bean define as configurações e toda a aplicação
@EnableWebSecurity //Permime personalizar as configurações
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	SecurityFilter securityFilter;
	
	@Bean //Filtros padrões de APIs REST
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) //Disabilita csrf
		//STATELESS: não armazena o token, a autenticação é feita em cada requisicão
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
		//Permite que requisições para /auth/login e /auth/register sejam acessadas sem autenticação
		.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/register").permitAll().anyRequest().authenticated())
		//Adiciona o securityFilter antes do filterAuthentication, para validar o token JWT para cada requisição
		.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	//Gera um hash para criptografar e validar as senhas
	@Bean
	public PasswordEncoder passordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	//Componente reponsável pela autenticação
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();

	}
}
