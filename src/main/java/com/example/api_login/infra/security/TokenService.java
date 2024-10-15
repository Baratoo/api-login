package com.example.api_login.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.api_login.model.entities.User;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			String token = JWT.create()
					.withIssuer("api-login") //Essa api é a emissora do token
					.withSubject(user.getEmail()) //O token é o email do usuario
					.sign(algorithm); //Assinatura do token
			return token;
					
		} catch (JWTCreationException ex) {
			throw new RuntimeException("Erro na autenticação");
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("api-login")
					.build()
					.verify(token) //Verifica se é válido e retorna um objeto JWT decodificado
					.getSubject(); //Extrai e retorna o email
		} catch (JWTVerificationException ex) {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
