package com.example.api_login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_login.dto.LoginRequestDTO;
import com.example.api_login.dto.ResponseDTO;
import com.example.api_login.infra.security.TokenService;
import com.example.api_login.model.entities.User;
import com.example.api_login.repositories.IUserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestDTO body) {
		User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not Found"));
		if(passwordEncoder.matches(user.getSenha(), body.senha())) {
			String token = this.tokenService.generateToken(user);
			return ResponseEntity.ok(new ResponseDTO(user.getNome(), token));
		}
		return ResponseEntity.badRequest().build();
	}
	
	/*
	@PostMapping("/login")
	public ResponseEntity register(@RequestBody LoginRequestDTO body) {
		User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not Found"));
		if(passwordEncoder.matches(user.getSenha(), body.senha())) {
			String token = this.tokenService.generateToken(user);
			return ResponseEntity.ok(new ResponseDTO(user.getNome(), token));
		}
		return ResponseEntity.badRequest().build();
	}*/
	
	
	
	
	
	
	
	
	
	
	
}
