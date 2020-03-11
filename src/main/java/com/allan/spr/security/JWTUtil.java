package com.allan.spr.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	//JWT - JWT (JSON Web Token) é um método RCT 7519 padrão da indústria para realizar autenticação entre duas partes por meio de um token assinado que autentica uma requisição web. 
	//Esse token é um código em Base64 que armazena objetos JSON com os dados que permitem a autenticação da requisição.
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	
	public String generateToken(String username) {
		//gera token é acrescenta tempo de expirar.
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
		
	}
	
	public boolean tokenValido(String token) {
		//Verifica se token é valido.
		//Claims - armazena as revindicações do token.(no meu caso alegando que e o susurio e seu tempo de expiração).
		Claims claims = getClaims(token);
		if(claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if(username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
			
		}
		return false;
	}
	
	
	public String getUsername (String token) {
		//pegar usuario aprtir do token
		Claims claims = getClaims(token);
		if(claims != null) {
			return claims.getSubject();
		}
		
		return null;
	}
	
	
	

	private Claims getClaims(String token) {
		//Obter os Claims(informações contidas) apartir de um token.
		
		try {
			
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
			
		} catch (Exception e) {
			return null;
		}
		
		
	}
	

}
